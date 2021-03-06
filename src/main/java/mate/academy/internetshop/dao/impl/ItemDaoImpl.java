package mate.academy.internetshop.dao.impl;

import java.util.List;
import java.util.Optional;
import mate.academy.internetshop.dao.IdGenerator;
import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.db.Storage;
import mate.academy.internetshop.exception.DataProcessingException;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;

@Dao
public class ItemDaoImpl implements ItemDao {

    @Override
    public Item create(Item item) {
        item.setId(IdGenerator.getItemId());
        Storage.items.add(item);
        return item;
    }

    @Override
    public Optional<Item> get(Long id) {
        return Storage.items
                .stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    @Override
    public Item update(Item item) throws DataProcessingException {
        Item old = get(item.getId()).orElseThrow(() ->
                        new DataProcessingException("Can't update item with id "
                                + item.getId()));
        int index = Storage.items.indexOf(old);
        return Storage.items.set(index, item);
    }

    @Override
    public boolean deleteById(Long id) throws DataProcessingException {
        Item old = get(id).orElseThrow(() ->
                        new DataProcessingException("Can't delete item with id " + id));
        return delete(old);
    }

    @Override
    public boolean delete(Item item) {
        return Storage.items.remove(item);
    }

    @Override
    public List<Item> getAll() {
        return Storage.items;
    }
}
