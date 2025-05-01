import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.io.Serializable;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;


public class GameModel{

    // Instance Variables

    static long serialVersionUID = 1L; // ID to verify class compatibility

    int total_cards; // number of cards in the game
    ArrayList<String> pets; // array that stores shuffled pet names
    ArrayList<Boolean> matched; // array that tracks match status of each pet
    ArrayList<String> pets_names = new ArrayList<>(
        Arrays.asList("🐶", "🐱", "🐰", "🐟", "🐴", "🐹")
    );

    // Constructor
    GameModel(String difficulty) {
        if (difficulty.equalsIgnoreCase("easy")) {
            this.total_cards = 6;
        } else if (difficulty.equalsIgnoreCase("medium")) {
            this.total_cards = 12;
        } else if (difficulty.equalsIgnoreCase("hard")) {
            this.total_cards = 20;
        } else {
            this.total_cards = 12; // default
        }
   
        matched = new ArrayList<>();
        pets = new ArrayList<>();
       
        // Add pairs
        for (int i = 0; i < total_cards / 2; i++) {
            String pet = pets_names.get(i % pets_names.size());
            pets.add(pet);
            pets.add(pet);
        }
   
        Collections.shuffle(pets);
   
        for (int i = 0; i < total_cards; i++) {
            matched.add(false);
        }
    }

    // Instance Methods
    void initializePets(){
        // initalizes a string array to store all the pet cards
        ArrayList<String> all_pets = new ArrayList<>();


        // add pets to array (every pet appears twice to form a pair)
        for(int i = 0; i < total_cards / 2; i++){
            String pet = pets_names.get(i % pets_names.size()); // cycles through pet names
            all_pets.add(pet);
            all_pets.add(pet);
        }

        // used shuffle from collections library to randomize pet placement
        Collections.shuffle(all_pets);
        pets.addAll(all_pets);
    }

    String revealPet(int index){
        // returns the pet value at a specific index in the array
        return pets.get(index);
    }

    boolean checkMatch(int index_one, int index_two){
        // handles case where user presses the same card twice
        if(index_one == index_two){
            return false;
        }

        // gets the pet names at the selected card indices
        String pets_index_one = pets.get(index_one);
        String pets_index_two = pets.get(index_two);

        // variable to track if the pair is a match
        boolean is_match = false;

        // if the pair matches set the variable to true and mark both indices as true
        if(pets_index_one.equals(pets_index_two)){
            is_match = true;
            matched.set(index_one, true);
            matched.set(index_two, true);
        }
        return is_match;
    }

    boolean allMatched(){
        // checks if all cards have been matched (win condition)
        // iterates through matched based on index
        for(int i = 0; i < matched.size(); i++){
            // if a card is found that hasnt been matched yet returns false
            if(matched.get(i) == false){
                return false;
            }
        }
        // if all cards are matched returns true
        return true;
    }

    int getTotalCards(){
        // simple getter function that returns the total cards variable
        return total_cards;
    }
    
    // saves current GameModel object to a specified file
    void saveGame(String filename){
        try{

            // creates output stream to write to file
            FileOutputStream file_out = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file_out);

            // writes current GameModel object to the file
            out.writeObject(this);

            // closes both streams 
            file_out.close();
            out.close();

            System.out.println("game saved to " + filename);
        }
        catch (IOException e){

            // catch exceptions (such as file not found, access denied, etc) and prints error message
            System.out.println("failed to save game");
            e.printStackTrace();

        }
    }

    // loads GameModel object from a specified file
    static GameModel loadGame(String filename){
        try{

            // creates input stream to read from file
            FileInputStream file_in = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file_in);

            // reads object from file, explicitly casts back to GameModel
            GameModel loaded = (GameModel) in.readObject();

            // closes both streams 
            file_in.close();
            in.close();
            
            System.out.println("game loaded from " + filename);
            return loaded;
        }
        catch (IOException | ClassNotFoundException e){

            // catch exceptions (in reading file or casting object) and prints error message
            System.out.println("failed to load game");
            return null;

        }
    }

}

