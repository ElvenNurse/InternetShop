package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.dao.IdGenerator;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.User;

@Dao
public class BucketDaoImpl implements BucketDao {
    @Override
    public Bucket create(Bucket bucket) {
        bucket.setId(IdGenerator.getBucketId());
        Storage.buckets.add(bucket);
        return bucket;
    }

    @Override
    public Optional<Bucket> get(Long id) {
        return Storage.buckets
                .stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();
    }

    @Override
    public Bucket update(Bucket bucket) throws DataProcessingException {
        Bucket old = get(bucket.getId()).orElseThrow(() ->
                new DataProcessingException("Can't update bucket with id "
                        + bucket.getId()));
        int index = Storage.buckets.indexOf(old);
        return Storage.buckets.set(index, bucket);
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        Bucket old = get(id).orElseThrow(() ->
                        new DataProcessingException("Can't delete bucket with id " + id));
        return delete(old);
    }

    @Override
    public boolean delete(Bucket bucket) {
        return Storage.buckets.remove(bucket);
    }

    @Override
    public List<Bucket> getAll() {
        return Storage.buckets;
    }

    @Override
    public Optional<Bucket> getByUser(User user) {
        return Storage.buckets
                .stream()
                .filter(b -> b.getUserId().equals(user.getId()))
                .findFirst();
    }
}
