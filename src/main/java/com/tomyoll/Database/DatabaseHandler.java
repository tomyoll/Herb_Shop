package com.tomyoll.Database;


import java.sql.*;

/**
 * �������� �� ����� �����
 */
public class DatabaseHandler extends Configs {

   public static Connection connection;

    public Connection getConnection() {
        return connection;
    }

    /**
     * ϳ��������� �� ���� �����
      */
    public void initConnection() {
        String connecton_string = "jdbc:mysql://" + dbHost + ":"
                + dbPort + "/" + dbName+"?autoReconnect=true";

        try {
            connection = DriverManager.getConnection(connecton_string,
                    dbUser, dbPass);

            Configs.ConnectStatus = true;

        }
        catch (SQLException exception)
        {
            Configs.ConnectStatus = false;
        }
    }

    /**
     * ��������� �����������
     * @param login
     * @param password
     * @return - ������ �����
     */
        public ResultSet getUser(String login, String password)
        {
            ResultSet resSet = null;

            String select = "SELECT * FROM Admins WHERE login =? AND password =?";

            try {
                PreparedStatement prSt = getConnection().prepareStatement(select);
                prSt.setString(1, login);
                prSt.setString(2, password);
                resSet =  prSt.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return resSet;
        }

    /**
     * ��������� �������� �����������
     * @return ������ �����
     */
    public ResultSet get_category()
        {
            ResultSet resultSet = null;
            String select = "SELECT * FROM diseases_category";

            try {
                PreparedStatement prSt = getConnection().prepareStatement(select);
                resultSet = prSt.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return resultSet;
        }

    /**
     * ��������� ������������ �� ��������
     * @param category ��������
     * @return ������ �����
     */
    public ResultSet get_disease_by_category(String category)
        {
            ResultSet resultSet = null;
            String select = "SELECT * FROM diseases INNER JOIN diseases_category ON" +
                    " diseases.CategoryID = diseases_category.id_category WHERE diseases_category.name =?";

            try {
                PreparedStatement prSt = getConnection().prepareStatement(select);
                prSt.setString(1, category);
                resultSet = prSt.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return resultSet;
        }

    /**
     * ��������� �� �� �������
     * @return ������ �����
     */
    public ResultSet get_actions()
        {

            ResultSet resultSet = null;
            String select = "SELECT * FROM actions";

            try {
                PreparedStatement prSt = getConnection().prepareStatement(select);
                resultSet = prSt.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return resultSet;
        }

    /**
     * ��������� ������ ���������
     * @return ������ �����
     */
    public ResultSet get_ingredients()
        {
            ResultSet resultSet = null;
            String select = "SELECT * FROM ingredients";

            try {
                PreparedStatement prSt = getConnection().prepareStatement(select);
                resultSet = prSt.executeQuery();
            } catch (Exception throwables) {
                throwables.printStackTrace();
            }
            return resultSet;
        }

    /**
     * ��������� ���������
     * @param name ��'� ���������
     * @param image �����������
     */
    public void add_product(String name, String image)
    {
        String insert = "INSERT INTO Production (product_id, product_name, image_path) VALUES (NULL, ?, ?)";

        try {
            PreparedStatement prSt = getConnection().prepareStatement(insert);
            prSt.setString(1, name);
            prSt.setString(2, image);
            System.out.println(prSt.toString());
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * ��������� id ��������� �� ������
     * @param name �����
     * @return ������ �����
     */
    public ResultSet get_product_id(String name)
    {
        ResultSet resultSet = null;
        String select = "SELECT Production.product_id AS id FROM Production WHERE Production.product_name =?";

        try {
            PreparedStatement prSt = getConnection().prepareStatement(select);
            prSt.setString(1, name);
            resultSet = prSt.executeQuery();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * ��������� id ������������ �� ������
     * @param name �����
     * @return ������ �����
     */
    public ResultSet get_disease_id(String name)
    {
        ResultSet resultSet = null;
        String select = "SELECT id AS id FROM diseases WHERE diseases.name in (" + name + ") GROUP BY id";

        try {
            PreparedStatement prSt = getConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * ��������� id 䳿 �� ������
     * @param name �����
     * @return ������ �����
     */
    public ResultSet get_action_id(String name)
    {
        ResultSet resultSet = null;
        String select = "SELECT id_action AS id FROM actions WHERE actions.name in (" + name + ") GROUP BY id";

        try {
            PreparedStatement prSt = getConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * ������ ������������
     * @param query �����
     */
    public void add_disease_product(String query)
    {
        try {
            PreparedStatement prSt = getConnection().prepareStatement(query);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * ������ �� �� ������
     * @param query �����
     */
    public void add_action_product(String query)
    {
        try {
            PreparedStatement prSt = getConnection().prepareStatement(query);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * ��������� �����䳺���
     * @param query �����
     */
    public void add_ingredients_product(String query)
    {
        try {
            PreparedStatement prSt = getConnection().prepareStatement(query);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * ��������� �� �����
     * @param name �����
     * @param type ���
     */
    public void delete_product(String name, int type)
    {
        String delete = null;
        switch (type)
        {
            case (0):
                delete = "DELETE FROM Production WHERE product_name =?";
            break;
            case(1):
                delete = "DELETE FROM diseases_category WHERE  name =?";
                break;
            case(2):
                delete = "DELETE FROM ingredients  WHERE name =?";
                break;
            case(3):
                delete = "DELETE FROM diseases WHERE name =?";
                break;
            case(4):
                delete = "DELETE FROM actions WHERE name =?";
                break;
        }

        try {
            PreparedStatement prSt = getConnection().prepareStatement(delete);
            prSt.setString(1, name);
            prSt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * �������� id �����䳺��� �� ������
     * @param name �����
     * @return ������ �����
     */
    public ResultSet get_ingredient_id(String name) {
        ResultSet resultSet = null;
        String select = "SELECT id AS id FROM ingredients WHERE name in (" + name + ") GROUP BY id";
        try {
            PreparedStatement prSt = getConnection().prepareStatement(select);
            resultSet = prSt.executeQuery();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * ��������� ������� ������������
     * @param name �����
     * @return ������ �����
     */
    public ResultSet get_category(String name) {
        ResultSet resultSet = null;
        String select = "SELECT id_category AS id FROM diseases_category WHERE name =?";

        try {
            PreparedStatement prSt = getConnection().prepareStatement(select);
            prSt.setString(1, name);
            resultSet = prSt.executeQuery();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    /**
     * ������ ������ �� �����
     * @param value �����
     * @param filter ����� �������
     * @param all ������� �� ��������
     * @return ������ �����
     */
    public ResultSet Filter(String value, int filter, boolean all) {
        ResultSet resultSet = null;
        String select =null;

        switch (filter) {
            case  (0):
                select = "SELECT * FROM Production";
                break;

            case  (1):
                select = "SELECT Production.* FROM ingredients_products INNER JOIN ingredients" +
                        " ON ingredients_products.id_ingredient = ingredients.id INNER JOIN Production" +
                        " ON ingredients_products.id_product = Production.product_id WHERE ingredients.name =?";
                break;

            case (2):
                select = "SELECT Production.* FROM diseases_product INNER JOIN diseases" +
                        " ON diseases_product.id_disease = diseases.id INNER JOIN Production" +
                        " ON diseases_product.id_product = Production.product_id WHERE diseases.name =?";
                break;

            case (3):
                select = "SELECT Production.* FROM actions_product INNER JOIN actions" +
                        " ON actions_product.id_action = actions.id_action INNER JOIN Production" +
                        " ON actions_product.id_product = Production.product_id WHERE actions.name =?";
                break;

            case (4):
                select = "SELECT * FROM Production WHERE product_name LIKE ?";
                break;
        }
            try {
                PreparedStatement prSt = getConnection().prepareStatement(select);
                if (!all && filter != 4) {
                    prSt.setString(1, value);
                }
                if(filter == 4)
                {
                    prSt.setString(1, '%'+value+'%');
                }

                    resultSet = prSt.executeQuery();

            } catch (Exception throwables) {
                throwables.printStackTrace();
            }

        return resultSet;
    }

    /**
     * ��������� ��������� ���������
     * @param name �����
     * @param type ��� ���������
     * @param category_id id �������
     * @param category �� ������� ��������
     */
    public void add_other(String name, int type, int category_id, boolean category)
    {
        String insert =null;

        switch (type) {
            case  (0):
                insert = "INSERT INTO actions (id_action, name) VALUES (NULL, ?)";
                break;

            case  (1):
                insert = "INSERT INTO ingredients (id, name) VALUES (NULL, ?)";
                break;

            case (2):
                insert = "INSERT INTO diseases (id, CategoryID, name) VALUES (NULL, ?, ?)";
                break;
            case (3):
                insert = "INSERT INTO diseases_category (id_category, name) VALUES (NULL, ?)";
                break;

        }

        try {
            PreparedStatement prSt = getConnection().prepareStatement(insert);
            if(!category) {
                prSt.setString(1, name);
            }
            else {
                prSt.setInt(1, category_id);
                prSt.setString(2, name);
            }
            prSt.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
