package mate.academy.internetshop.service.impl;

import java.util.List;
import java.util.NoSuchElementException;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.lib.Inject;
import mate.academy.internetshop.lib.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.User;
import mate.academy.internetshop.service.BucketService;

@Service
public class BucketServiceImpl implements BucketService {
    @Inject
    private static BucketDao bucketDao;

    @Override
    public Bucket create(Bucket bucket) {
        return bucketDao.create(bucket);
    }

    @Override
    public Bucket get(Long id) {
        return bucketDao.get(id).orElseThrow(() ->
                new NoSuchElementException("Can't find bucket with id " + id));
    }

    @Override
    public Bucket getByUser(User user) {
        return getAll()
                .stream()
                .filter(b -> b.getUserId().equals(user.getId()))
                .findFirst()
                .orElse(create(new Bucket(user)));
    }

    @Override
    public Bucket update(Bucket bucket) {
        return bucketDao.update(bucket);
    }

    @Override
    public boolean deleteById(Long id) {
        return bucketDao.deleteById(id);
    }

    @Override
    public boolean delete(Bucket bucket) {
        return bucketDao.delete(bucket);
    }

    @Override
    public List<Bucket> getAll() {
        return bucketDao.getAll();
    }

    @Override
    public void addItem(Bucket bucket, Item item) {
        bucket.addItem(item);
        update(bucket);
    }

    @Override
    public void deleteItem(Bucket bucket, Item item) {
        bucket.deleteItem(item);
        update(bucket);
    }

    @Override
    public void clear(Bucket bucket) {
        bucket.clear();
        update(bucket);
    }

    @Override
    public List<Item> getAllItems(Bucket bucket) {
        return bucket.getItems();
    }
}
