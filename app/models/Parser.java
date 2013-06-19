package models;

import de.phoenix.database.entity.User;

import play.data.DynamicForm;

/**
 * Fills the different entities with information
 * 
 * @author Markus W.<br>Matthias E.
 */
public class Parser {
        
    /**
     * Parses user data from given DynamicForm to user entity and also <br>
     * checks for user data correctness.
     * 
     * @param DynamicForm 
     * @return de.phoenix.database.entity.User
     */    
    public static User setUser(DynamicForm dynForm)
    {
        DynamicForm requestData = dynForm;
        User user = new User(requestData.get("password"));
        
        String name = requestData.get("name");
        String surname = requestData.get("surname");
        String title = requestData.get("title");
        String username = requestData.get("username"); 
        String email = requestData.get("email");
        
        if (!requestData.get("password").equals(requestData.get("repassword")))
        {
            //TODO: Throw exeption
        }
        
        if (requestData.get("password").length()<6)
        {
            //TODO: Throw exeption
        }
        
        if(name.length()>0)
            user.setName(name);
        //TODO: else throw exeption
        if(surname.length()>0)
            user.setSurname(surname);
        //TODO: else throw exeption
        if(title.length()>0)
            user.setTitle(title);
        //TODO: else throw exeption
        if(username.length()>0)
            user.setUsername(username);
        //TODO: else throw exeption        
        if(email.length()>0)
            user.setEmail(email);
        //TODO: else throw exeption
                
        return user;
    }
    
    
}
