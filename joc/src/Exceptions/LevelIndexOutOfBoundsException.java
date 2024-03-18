package Exceptions;

public class LevelIndexOutOfBoundsException extends Exception{

    LevelIndexOutOfBoundsException(String s){
            super(s);

    }
    public static class TestCustomException {
        public static void validate(int lvlIndex) throws LevelIndexOutOfBoundsException {
            if (lvlIndex <1 || lvlIndex >3)
                throw new LevelIndexOutOfBoundsException("Level Index not valid");
            else
                System.out.println("New level loaded");
        }
    }
}
