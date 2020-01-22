package mate.academy.internetshop.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import mate.academy.internetshop.dao.ItemDao;
import mate.academy.internetshop.lib.Dao;
import mate.academy.internetshop.model.Item;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Dao
public class ItemDaoJdbcImpl extends AbstractDao<Item> implements ItemDao {
    private static Logger logger = LogManager.getLogger(ItemDaoJdbcImpl.class);
    private static String DB_NAME = "internetshop";

    public ItemDaoJdbcImpl(Connection connection) {
        super(connection);
    }

    @Override
    public Item create(Item entity) {
        String query = String.format(Locale.ROOT,
                "INSERT INTO %s.items (name, price) VALUES ('%s', '%.4f');",
                DB_NAME, entity.getName(), entity.getPrice());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can't create item", e);
        }
        return entity;
    }

    @Override
    public Optional<Item> get(Long id) {
        String query = String.format("SELECT * FROM %s.items WHERE item_id = %d;", DB_NAME, id);
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                return Optional.of(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't get item by ID " + id, e);
        }
        return Optional.empty();
    }

    @Override
    public Item update(Item entity) {
        String query = String.format(Locale.ROOT,
                "UPDATE %s.items SET name = '%s', price = '%.4f' WHERE item_id = '%d';",
                DB_NAME, entity.getName(), entity.getPrice(), entity.getId());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException e) {
            logger.warn("Can't update item", e);
        }
        return entity;
    }

    @Override
    public boolean deleteById(Long id) {
        String query = String.format("DELETE FROM %s.items WHERE item_id = '%d';",
                DB_NAME, id);
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            logger.warn("Can't delete item with ID " + id, e);
        }
        return false;
    }

    @Override
    public boolean delete(Item entity) {
        String query = String.format("DELETE FROM %s.items WHERE item_id = '%d';",
                DB_NAME, entity.getId());
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(query);
            return true;
        } catch (SQLException e) {
            logger.warn("Can't delete item", e);
        }
        return false;
    }

    @Override
    public List<Item> getAll() {
        List<Item> items = new ArrayList<>();
        String query = String.format("SELECT * FROM %s.items;", DB_NAME);
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Long itemId = rs.getLong("item_id");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                Item item = new Item(name, price);
                item.setId(itemId);
                items.add(item);
            }
        } catch (SQLException e) {
            logger.warn("Can't get items", e);
        }
        return items;
    }
}
