# Supernova Saga

Modified Clone of Stick Hero in Java made as a part of the course project for CSE201: Advanced Programming course conducted in Monsoon 2023 at IIIT Delhi by Group 116

## Team Members:
Parth Garg (2022351)
Swarnima Prasad (2022525)

## Features

1. *Threading for Dynamic Actions:*
    - Threaded processes for:
        - Increasing the stick length.
        - Moving the hero.
        - Flipping the hero.

2. *Background Music:*
    - Background music enhances the gaming experience.
    - The game uses a music file that plays continuously in the background.

3. *Animation Effects:*
    - Multiple animations implemented, including falling effects when the hero misses a platform.

4. *Design Patterns:*
    - *Singleton Pattern:*
        - Implemented to ensure only one instance of the hero exists.
    - *Factory Pattern:*
        - Used for creating instances of objects with image views.

5. *Revive Feature:*
    - If the player collects at least 3 stars, a revive feature is activated for the character.

6. *Choose Your Character:*
    - Bonus feature allowing players to choose their own hero character.

7. *Instruction Screen:*
    - Provides players with instructions on how to play the game.

8. *Scoring System:*
    - Each collected star increases the player's score by one point. 
    - Each successful travel increases the player's score by one point.

9. *Serialization:*
    - Serializable interface used for storing an array of saved games for future use.

10. *JUnit Testing:*
    - The project includes JUnit tests to ensure the correctness of key functionalities.

## How to Play

1. Launch the game.
2. Extend the stick by clicking and holding the mouse.
3. Release the mouse to move the hero. Aim to land on platforms.
4. Use the space bar to perform flips.
5. Collect cherries to increase your score and unlock the revive feature.
