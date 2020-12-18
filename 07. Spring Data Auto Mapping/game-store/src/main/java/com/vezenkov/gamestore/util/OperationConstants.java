package com.vezenkov.gamestore.util;

public abstract class OperationConstants {
    public static final String READ_LINE_ARGS_MSG = "Enter arguments separated by '|':";

    public static final String SPLITTER_REGEXP = "\\|";

    public static final String EXIT = "exit";

    public static final String INVALID_ARGS_MSG = "Expected %d line args, received %d. Try again!%n";

    public static final String REGISTER_USER = "RegisterUser";

    public static final int REGISTER_USER_LINE_ARGS = 5;

    public static final String SUCCESSFULLY_REGISTERED_USER_MSG = "%s was registered%n";

    public static final String PASSWORDS_MISMATCH_MSG = "Passwords do not match!";

    public static final String LOGIN_USER = "LoginUser";

    public static final int LOGIN_USER_LINE_ARGS = 3;

    public static final String SUCCESSFULLY_LOGGED_ID = "Successfully logged in %s%n";

    public static final String INCORRECT_CREDENTIALS = "Incorrect username / password";

    public static final String LOGOUT = "Logout";

    public static final String SUCCESSFULLY_LOGOUT_MSG = "User %s successfully logged out%n";

    public static final String UNSUCCESSFUL_LOGOUT_MSG = "Cannot log out. No user was logged in.";

    public static final String ADD_GAME = "AddGame";

    public static final String NOT_ADMIN_ERR = "Cannot do this operation. You have no admin rights! ";

    public static final int ADD_GAME_LINE_ARGS = 8;

    public static final String SUCCESSFULLY_ADDED_GAME = "Added %s%n";

    public static final String EDIT_GAME = "EditGame";

    public static final int MIN_EDIT_GAME_ARGS = 3;

    public static final String GAME_EDITED_SUCCESSFULLY = "Edited %s%n";

    public static final String GAME_NOT_EDITED = "Edit failed. Try again!";

    public static final String DELETE_GAME = "DeleteGame";

    public static final int DELETE_GAME_ARGS = 2;

    public static final String SUCCESSFULLY_DELETED = "Deleted %s%n";

    public static final String ALL_GAMES = "AllGames";

    public static final int ALL_GAMES_ARGS = 1;

    public static final String DETAIL_GAME = "DetailGame";

    public static final int DETAIL_GAME_ARGS = 2;

    public static final String NO_SUCH_GAME = "No such game in the store";

    public static final String OWNED_GAMES = "OwnedGames";

    public static final String NO_OWNED_GAMES = "Your game library is empty! Go ahead and buy one!";

    public static final String ADD_ITEM = "AddItem";

    public static final int ADD_ITEM_ARGS = 2;

    public static final String SUCCESSFULLY_ADDED_GAME_TO_CART = "%s added to cart.%n";

    public static final String UNSUCCESSFULLY_ADDED_OR_REMOVED_GAME_TO_CART = "An error occurred while adding the game to the cart! Try again!";

    public static final String REMOVE_ITEM = "RemoveItem";

    public static final int REMOVE_ITEM_ARGS = 2;

    public static final String SUCCESSFULLY_REMOVED_GAME_FROM_CART = "%s removed from cart.%n";

    public static final String BUY_ITEM = "BuyItem";

    public static final String SUCCESSFULLY_BOUGHT_GAMES = "Successfully bought games:";

    public static final String ERROR_DURING_PAYMENT = "An error has occurred during the process!";
}
