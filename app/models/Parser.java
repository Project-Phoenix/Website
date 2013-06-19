package models;

import exceptions.TooShortPasswordException;
import exceptions.PasswordMismatchException;
import exceptions.EmptyFieldException;

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
     * @throws TooShortPasswordException 
     * @throws EmptyFieldException 
     * @throws PasswordMismatchException 
     */    
    public static User setUser(DynamicForm dynForm) throws TooShortPasswordException, EmptyFieldException, PasswordMismatchException
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
            throw new PasswordMismatchException();
        }
        
        if (requestData.get("password").length()<6)
        {
            throw new TooShortPasswordException();
        }
        
        if(name.length()>0)
            user.setName(name);
        else throw new EmptyFieldException();
        if(surname.length()>0)
            user.setSurname(surname);
        else throw new EmptyFieldException();
        if(title.length()>0)
            user.setTitle(title);
        else throw new EmptyFieldException();      
        if(username.length()>0)
            user.setUsername(username);
        else throw new EmptyFieldException();        
        if(email.length()>0)
            user.setEmail(email);
        else throw new EmptyFieldException(); 
                
        return user;
    }
    
    
}
