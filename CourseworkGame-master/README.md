*Compilation Instructions:*
Prerequisites:
Java Development Kit (JDK): Make sure you have Java JDK installed on your machine. You can download it from the official Oracle website or use OpenJDK.
Maven: Ensure that Maven is installed on your system. You can download it from the official Apache Maven website.
Compilation quide:
1. Clone the repository:
git clone https://github.com/DinaraAya/COMP2042_CW_efyda2DinaraAyaganova.git
2. Navigate to the project repository:
cd COMP2042_CW_efyda2DinaraAyaganova
3. Build a project with Maven:
Run the following Maven command to compile the project and create an executable JAR file:
mvn clean package
This command will download the project dependencies, compile the source code, and package the application into a JAR file.
4. Run the game:
Once the build is successful, you can run the game using the following command:
java -jar target/brick-breaker-game-1.0-SNAPSHOT.jar
Replace brick-breaker-game-1.0-SNAPSHOT.jar with the actual name of the generated JAR file.
Alternatively, you can navigate to the target directory and run the JAR file directly:
cd target
java -jar brick-breaker-game-1.0-SNAPSHOT.jar

*Implemented and working correctly:*

1. GUI Changes:
Description: Enhanced visual appeal by changing the font, background, button size and shapes, and adding rectangles for the pause menu, settings, and game over scenes.
2. Cracking Blocks:
Description: Introduced a new playable feature where blocks crack on the first hit and are destroyed on the second. Score increases only when completely destroying a block.
3. Highest Score Tracking:
Description: Implemented a feature that keeps track of the highest score ever achieved.
4. Game Over Scene:
Description: Modified the game over scene to display the achieved score, highest score, and a restart button.
5. Countdown Display:
Description: Added a countdown display before starting a new game to prevent unexpected starts.
6. Slide-Up Animations:
Description: Implemented slide-up animations for transitioning between scenes like pause and settings.
7. Main Menu:
Description: Implemented a main menu with settings, load game, and quit buttons.
8.Settings Button:
Description: Added settings button with two sliders and a resume button that takes the user back to the main menu.
9. Quit Button:
Description: Successfully exits the game without terminating it through the IDE.
10. Pause State:
Description: Implemented a pause state that freezes the game.
11. Pause Menu:
Description: Added a pause menu with home, restart, and resume buttons, along with two sliders.
12. Home Button:
Description: Takes the user back to the main menu.
13. Restart Button:
Description: Restarts the game without taking the user to the main menu.
14. Resume Button:
Description: Resumes the game from the pause state.
15. Background Music:
Description: : Added background music with a slider for volume control, and ensured slider values remain unchanged during game restarts or access from menu or settings.
16. Sound Effects:
Description: Included sound effects for button presses, game loss, and catching bonus blocks.

*Implemented but not working properly:*

1. Load from save functionality:
Description: If after pressing an 'S' key, user goes back to the main menu through home button, and presses the load button, the loaded score will only appear, after first block was hit.
However, if users quits the game after saving and loads the game that way, it works fine.
Steps to address the problem: I tried to change the way home button and load from save method work, it made score appear after the first block was hit, but did not solve the issue completely.
2. Custom Font:
Description: Sometimes custom font is not loaded properly on a duplicate folder or when a user attempts to run it on different laptop.
Steps to address the problem: I attempted reuploading it to the recources and target folder and it solves the issue temporarily. 

*Features not implemented:*

1. Different block patterns for each level:
Description: Addition of new block patterns for each level
Why left out: Implementing new block patterns for each level was time-consuming, code became too large and complex.

*New Java Classes:*

1. GameModel: 
Location: CourseworkGame-master/src/main/java/brickGame/GameModel.java
Description: The GameModel class is the core component responsible for managing the game logic.
It controls various aspects of the game, including the ball's movement, collisions with blocks, scoring, level progression, and interactions with bonuses. 
The class interacts with user input through a GameController. Additionally, it utilizes a GameEngine for handling game updates and physics. 
The class supports functionalities such as starting and stopping the game engine, moving the paddle, handling collisions, advancing to the next level, and saving/loading game progress.
2. GameView:
Location: CourseworkGame-master/src/main/java/brickGame/GameView.java
Description: The GameView class is responsible for managing the graphical user interface (GUI) elements of the brick-breaking game implemented using JavaFX.
It handles the display of various UI components, including buttons, labels, images, and rectangles. The class facilitates the transition between different screens, such as the main game screen, pause screen, settings screen, and game over screen.
Key components and functionalities of the GameView class include the initialization of UI elements, creation of buttons with associated images, definition of scenes for different screens, and management of animations for transitioning between screens. 
3. GameController:
Location: CourseworkGame-master/src/main/java/brickGame/GameController.java
Description: It handles user input events, such as key presses, and interacts with both the game model and the game view. 
The class also manages the initialization of button actions, slider controls for background music and sound effects.
4. Observable:
Location: CourseworkGame-master/src/main/java/brickGame/Observable.java
Description: The Observable class is a utility class that encapsulates various properties related to the state of the game. 
It utilizes JavaFX's property bindings to create observable properties for different aspects of the game, including the position of the paddle (xBreak and yBreak), the position of the ball (xBall and yBall),
the player's score (score), the game level (level), and the number of remaining lives (heart).
5. BgMusicManager:
Location: CourseworkGame-master/src/main/java/brickGame/BgMusicManager.java
Description: The BgMusicManager class is a singleton manager responsible for handling background music in the game. 
It uses JavaFX's MediaPlayer for managing and controlling the playback of background music. 
The class includes methods for initializing the background music, playing, stopping, pausing, setting the volume, and checking the current playback status.
6. SoundManager:
Location: CourseworkGame-master/src/main/java/brickGame/SoundManager.java
Description: The SoundManager class is a singleton manager responsible for managing and playing various sound effects in the game. 
It uses JavaFX's MediaPlayer for handling the playback of sound files. The class includes methods for playing different sound effects, such as button clicks, game over, and bonus sounds. 
Additionally, it allows for setting the volume of the sound effects and integrates with a JavaFX Slider for dynamic volume adjustment.
7. SliderManagerBgMusic:
Location: CourseworkGame-master/src/main/java/brickGame/SliderManagerBgMusic.java
Description: The SliderManagerBgMusic class is a singleton manager responsible for managing the volume control slider associated with background music in the game. 
8. SliderManagerSound: 
Location: CourseworkGame-master/src/main/java/brickGame/SliderManagerSound.java
Description: The SliderManagerBgMusic class is a singleton manager responsible for managing the volume control slider associated with sound effects in the game. 

*Modified Java Classes:*

1. Main.java:
Instead of initializing the game components directly in the start method, the Main class now creates instances of GameView, GameModel, and GameController in the start method. 
This improves the readability and maintainability of the code. I removed all the unused libraies and variables, since they are now handled in different classes.
2. Block.java:
Removed the import statement for javafx.scene.paint.Color in the modified code as it was no longer needed, because all the default blocks now have the same appearance.
Removed the private static Block block variable, for the same reason stated above.n the constructor, removed the Color color parameter, as it was not being used in the modified code.
Removed the implements Serializable interface from the class declaration, as there were no serialization-related operations in the class.
Added constants COOLDOWN_DURATION to represent the duration of the cooldown period between hits on a block, and hitCount to keep track of the number of hits on a block.
Modified onHit() method to handle block hit logic. It checks the cooldown duration between hits and updates the block appearance based on the hit count. If the hit count is 1, it changes the block appearance to a cracked pattern, and if the hit count is 2 or more,
it marks the block as destroyed and makes it invisible. Modified the draw() method to use different images based on the block type. Updated the images used for different block types, such as "choco_block.png," "heart_block.png," "gold_block.png," and "game_block.png."
3. Score.java:
Used lambda expressions for Runnable instances and replaced the traditional new Runnable() syntax with a shorter lambda syntax.
Utilized Platform.runLater() to update JavaFX UI components on the JavaFX Application Thread. Removed the import statement for sun.plugin2.message.Message, as it was commented out and not being used.
Replaced the explicit Thread.sleep(15) and animation loop with a Platform.runLater() call inside a loop for smoother animations.
Updated the animation logic to use finalI instead of i within the lambda expression. Removed the main parameter from the showGameOver and showWin methods, as it was not used in these methods.
Introduced file I/O operations to read and write the highest score to a file named "highscore.txt."
Created methods readHighestScore(), getHighestScore(), saveHighestScore(int highestScore), to handle reading, getting, and saving the highest score.
Modified the showGameOver method signature to accept a GameView and GameModel as parameters, allowing it to update the game over score and highest score.
4. GameEngine.java:
Added the volatile keyword to the isStopped variable to ensure proper visibility across threads. 
Utilized lambda expressions for Runnable instances to simplify the code.
Used Platform.runLater() to execute UI-related code (specifically, onAction.onPhysicsUpdate()) on the JavaFX Application Thread.
Simplified the thread interruption checks using the isStopped variable directly in the while loop conditions. Replaced the deprecated stop() method calls with setting the isStopped variable to true.

*Unexpected problems:*
During this assignment, I ran into an unexpected issue where I accidentally merged the wrong branch into the main branch. Unfortunately, I had forgotten to commit the recent changes I made before the merge. Thankfully, I had a duplicate working version of the code, serving as a backup.
To address the situation, I decided to perform a cleanup on GitHub. This involved removing the existing folder associated with the incorrect merge and replacing it with the duplicate working version. By doing so, I ensured that the main branch on GitHub reflected the intended changes.
This experience emphasized the importance of careful branch management and version control practices. It's crucial to double-check changes, commit them before merging, and verify the target branch. Additionally, having a backup or duplicate version proved to be a valuable safety net.