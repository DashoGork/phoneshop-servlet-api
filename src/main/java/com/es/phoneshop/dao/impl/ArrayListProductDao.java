package com.es.phoneshop.dao.impl;

import com.es.phoneshop.dao.ProductDao;
import com.es.phoneshop.exceptions.ProductNotFoundException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.web.ProductListPageParameters;
import com.es.phoneshop.web.SortOptions;
import com.es.phoneshop.web.SortOrder;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class ArrayListProductDao implements ProductDao {

    private static volatile ArrayListProductDao arrayListProductDao;

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Lock readLock = readWriteLock.readLock();
    private Lock writeLock = readWriteLock.writeLock();
    private List<Product> products;
    private long id;

    private ArrayListProductDao() {
        products = new ArrayList<>();
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
    public Product getProduct(Long id) throws ProductNotFoundException, IllegalArgumentException {
        readLock.lock();
        try {
            if (id != null) {
                Product requiredProduct;
                requiredProduct = products.stream().
                        filter((product -> id.equals(product.getId())
                                && product.isValid())).
                        findFirst().orElseThrow(() -> new ProductNotFoundException(id));
                return requiredProduct;
            } else
                throw new IllegalArgumentException();
        } finally {
            readLock.unlock();
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
    public void save(Product product) {
        writeLock.lock();
        try {
            if (product != null) {
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
    public List<Product> findProducts(String name) throws InvalidParameterException {
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
                        int a1 = 0, a2 = 0;
                        for (String word : words) {
                            a1 = a1 + o1.getDescription().indexOf(word) == -1 ? -1 : 1;
                            a2 = a2 + o2.getDescription().indexOf(word) == -1 ? -1 : 1;
                        }
                        if (a1 > a2) return -1;
                        else if (a1 < a2) return 1;
                        else return 0;
                    }
                }).collect(Collectors.toList());
                return productList;
            }
            throw new InvalidParameterException();
        } finally {
            readLock.unlock();
        }

    }

    @Override
    public List<Product> findProducts(String name, String sortField, String sortOrder) throws InvalidParameterException {
        readLock.lock();
        try {
            List<Product> listToSort = findProducts(name);
            Comparator<Product> priceComparator = Comparator.comparing(product -> product.getPrice());
            Comparator<Product> descriptionComparator = Comparator.comparing(product -> product.getDescription());
            Comparator comparator = sortField.equals(SortOptions.description.name()) ? descriptionComparator : priceComparator;
            listToSort = listToSort.stream().sorted(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return comparator.compare(o1, o2) * SortOrder.valueOf(sortOrder).getValue();
                }
            }).collect(Collectors.toList());
            return listToSort;
        } finally {
            readLock.unlock();
        }
    }
}

