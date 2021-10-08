package com.es.phoneshop.dao.product.impl;

import com.es.phoneshop.dao.AbstractDao;
import com.es.phoneshop.dao.product.ProductDao;
import com.es.phoneshop.enums.SortMatching;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.enums.SortOptions;
import com.es.phoneshop.enums.SortOrder;


import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ArrayListProductDao extends AbstractDao<Product> implements ProductDao {

    private static volatile ArrayListProductDao arrayListProductDao;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();
    private ArrayList<Product> products;
    private Long id;

    private ArrayListProductDao() {
        products = (ArrayList<Product>) super.getObjects();
        id = super.getId();
    }

    public static ArrayListProductDao getArrayListProductDao() {
        ArrayListProductDao result = arrayListProductDao;
        if (result != null) {
            return result;
        }
        synchronized (ArrayListProductDao.class) {
            if (arrayListProductDao == null) {
                arrayListProductDao = new ArrayListProductDao();
            }
            return arrayListProductDao;
        }
    }

    @Override
    public List<Product> findProducts() {
        readLock.lock();
        try {
            return products.stream().filter((product -> product.isValid())).collect(Collectors.toList());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void saveItem(Product product) {
        writeLock.lock();
        try {
            if (product != null && product.isValid()) {
                if (products.stream()
                        .anyMatch(productFromStream -> productFromStream.getId().equals(product.getId()))) {
                    update(product);
                } else {
                    product.setId(id++);
                    products.add(product);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(Long id) {
        writeLock.lock();
        try {
            products.removeIf(product -> id.equals(product.getId()));
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void update(Product product) {
        writeLock.lock();
        try {
            if (product != null) {
                Product productToUpdate = products.stream()
                        .filter((productFromStream -> product.getId().equals(productFromStream.getId())))
                        .findFirst()
                        .orElse(null);
                if (productToUpdate != null) {
                    int replace = IntStream.range(0, products.size())
                            .filter((productToStream -> products.get(productToStream).getId().equals(productToUpdate.getId()))).findFirst().getAsInt();
                    products.set(replace, product);
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<Product> findProducts(String name) throws IllegalArgumentException {
        readLock.lock();
        try {
            if (name != null) {
                String words[] = name.split(" ");
                List<Product> productList = new ArrayList<>();
                for (String word : words) {
                    productList.addAll(products.stream()
                            .filter((product -> product.getDescription().toLowerCase().contains(word.toLowerCase())))
                            .collect(Collectors.toList()));
                }
                productList = productList.stream().distinct().collect(Collectors.toList());
                productList = productList.stream().sorted(new Comparator<Product>() {
                    @Override
                    public int compare(Product o1, Product o2) {
                        return getNumberOfWordsInjections(words, o2.getDescription())
                                - getNumberOfWordsInjections(words, o1.getDescription());
                    }
                }).collect(Collectors.toList());
                return productList;
            }
            throw new IllegalArgumentException("searching field is null");
        } finally {
            readLock.unlock();
        }
    }

    private List<Product> findProductsMatching(List<Product> products, String name, SortMatching sortMatching) {
        readLock.lock();
        try {
            if (sortMatching.name().toLowerCase().equals("any")) {
                return products;
            } else {
                String words[] = name.split(" ");
                return products = products.stream().filter((product -> (getNumberOfWordsInjections(words, product.getDescription()) == words.length))).collect(Collectors.toList());
            }
        } finally {
            readLock.unlock();
        }
    }


    @Override
    public List<Product> findProducts(String name, BigDecimal minPrice, BigDecimal maxPrice, SortMatching match) throws InvalidParameterException {
        readLock.lock();
        try {
            if(name.isEmpty() || name==null){
                return findProducts();
            }
            if(minPrice.equals(new BigDecimal(-1)) & maxPrice.equals(new BigDecimal(-1))){
                List<Product> products = findProducts(name);
                return findProductsMatching(products, name, match);
            }
            List<Product> products = findProducts(name);
            products = findProductsMatching(products, name, match);
            products =products.stream()
                    .filter((product ->(product.getPrice().compareTo(minPrice) >= 0 && product.getPrice().compareTo(maxPrice) <= 0))).collect(Collectors.toList());
            Comparator<Product> comparator = Comparator.comparing(product -> product.getPrice());
            products = products.stream().sorted(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return comparator.compare(o1, o2);
                }
            }).collect(Collectors.toList());

            return products;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Product> findProducts(String name, String sortField, String sortOrder) throws IllegalArgumentException {
        readLock.lock();
        try {
            if (name != null) {
                List<Product> listToSort = findProducts(name);
                if (sortField != null & sortOrder != null) {
                    Comparator<Product> priceComparator = Comparator.comparing(product -> product.getPrice());
                    Comparator<Product> descriptionComparator = Comparator.comparing(product -> product.getDescription());
                    Comparator comparator;
                    if (SortOptions.DESCRIPTION.name().equals(sortField.toUpperCase(Locale.ROOT))) {
                        comparator = descriptionComparator;
                    } else if (SortOptions.PRICE.name().equals(sortField.toUpperCase(Locale.ROOT))) {
                        comparator = priceComparator;
                    } else {
                        throw new IllegalArgumentException("Invalid sort field " + sortField);
                    }
                    listToSort = listToSort.stream().sorted(new Comparator<Product>() {
                        @Override
                        public int compare(Product o1, Product o2) {
                            return comparator.compare(o1, o2) * SortOrder.valueOf(sortOrder.toUpperCase(Locale.ROOT)).getValue();
                        }
                    }).collect(Collectors.toList());
                }
                return listToSort;
            } else {
                return findProducts();
            }
        } finally {
            readLock.unlock();
        }
    }

    private int getNumberOfWordsInjections(String[] searchWords, String searchContainer) {
        return Arrays.stream(searchWords).reduce(0, (a, b) -> (searchContainer.contains(b) ? 1 : 0) + a, Integer::sum);
    }
}

