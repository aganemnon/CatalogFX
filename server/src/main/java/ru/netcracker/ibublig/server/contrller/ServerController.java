package ru.netcracker.ibublig.server.contrller;

import ru.netcracker.ibublig.model.Category;
import ru.netcracker.ibublig.model.User;

import java.io.File;
import java.util.ArrayList;

public class ServerController {
    private CategoryWrapper categoryWrapper;
    private UserWrapper userWrapper;

    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Category> categories = new ArrayList<>();

    private File fileUsers = new File("C:\\Data","Users.xml");
    private File catalogFile = new File("C:\\Data","Catalog.xml");

    public ServerController(CategoryWrapper categoryWrapper, UserWrapper userWrapper) {
        this.categoryWrapper = categoryWrapper;
        this.userWrapper = userWrapper;
    }

    public ServerController() {
        categoryWrapper = new CategoryWrapper(categories);
        userWrapper = new UserWrapper(users);

        categoryWrapper.loadPersonDataFromFile(catalogFile);
        userWrapper.loadPersonDataFromFile(fileUsers);

        //users.add(new User("Ilya","Sirotin","admin","qwerty",true));
        //users.add(new User("Dydya","Pupkin","dyadya","fsdfdf",false));
        //users.add(new User("Chel","Василий","chel","fllflflf",false));
        //userWrapper.savePersonDataToFile(fileUsers);

        System.out.println("Число загруженых юзеров " + users.size());
    }

    public void registrationUser(User user){
        users.add(user);
        userWrapper.savePersonDataToFile(fileUsers);
    }

    public User authorizationUser(User user){
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getLogin().equals(user.getLogin())){
                int idUser = i;
                if (users.get(idUser).getPassword().equals(user.getPassword())){
                    System.out.println("Успешная авторизация: " + user.getLogin());
                    User userSend;
                    userSend = users.get(idUser);
                    return userSend;
                }
            }
        }
        return null;
    }

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        CategoryWrapper wrapper = new CategoryWrapper(categories);
        wrapper.savePersonDataToFile(catalogFile);
        categoryWrapper.loadPersonDataFromFile(catalogFile);
    }

    public void addCategories(Category category){
        categories.add(category);
        categoryWrapper.savePersonDataToFile(catalogFile);
    }
}
