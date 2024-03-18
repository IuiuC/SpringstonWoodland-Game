package Exceptions;

public class PlayerTooCloseToBorderException extends Exception{

    PlayerTooCloseToBorderException(String s){
        super(s);

    }
    public static class TestCustomException {
        public static void validate(int rightB,int diff) throws PlayerTooCloseToBorderException {
           if(diff>rightB)
                throw new PlayerTooCloseToBorderException("The player is in an inaccessible zone");
        }
    }
}