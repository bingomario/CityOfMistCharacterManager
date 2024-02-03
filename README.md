# City of Mist Character Manager

## Paterson How - CPSC 210 Project

This project organizes characters for the ttrpg (tabletop role-playing game) *City of Mist*. Characters are comprised of a few elements, including:  
- **Themes**, which represent the core of a character
- **Tags**, which are abilities or traits that power a character's moves in game
- **Statuses**, which represent temporary states, such as injuries

This project will be both a character creator and manager, useful for players to track their ongoing characters as well as test ideas for new ones. It is also useful for an MC (Master of Ceremonies, the player who runs the game) to have all of their party member's characters in one place.

This project is of particular interest to me as I am an MC, and while many other ttrpgs have excellent digital tools (D&D Beyond being the most mainstream example) I have not found any for *City of Mist*.


## User Stories ##

- As a user, I want to be able to create a new party and add it to a list of parties
- As a user, I want to be able to create a new character and add it to a party
- As a user, I want to be able to select a character and add statuses, themes, and tags to that character
- As a user, I want to be able to select a character and remove statuses, themes, and tags from that character
- As a user, I want to remove a character from a party
- As a user, I want to remove an empty party from my list of parties
- As a user, I want to select a party and view a list of characters in that party
- As a user, I want to select a character and view a list of their statuses, themes and tags
- As a user, I want to be able to add build up (xp) to a character and increase their improvements (level)  
- As a user, I want to be able to save all of my changes to parties and their characters
- As a user, I want to be able to load a file with a set of parties and characters on it


## Instructions for Marker ##
- You can locate my visual component by starting the application, you will be presented with the splash screen image. The image will disappear when you click on the screen.
- You can add X (Characters) to Y (Parties) by starting the application and clicking through the splash screen. Then enter a party name in the text field, and press the enter key. The party should appear in the list. Click on the party in the list and click the "Select Party" button. You will now be in the party menu. To add characters to the party, enter a character name in the text field and press the Enter key.
- You can generate the first required action related to the story of "Adding X to Y" by following the instructions above to add a character to a party, then selecting that character in the list, then clicking the "Select Character" button. You will now be presented with the Character information screen, where you can add and modify themes of that character. 
- You can generate the second required action related to the story of "Adding X to Y" by following the instructions above to add a character to a party, then selecting that character in the list, then clicking the "Delete Character" button. This will delete the character from the party and the ui list.
- You can save the state of my application by adding Parties, Characters, and Themes, then clicking the "Save Parties" button in the main menu (you may need to exit panels to return).
- You can reload the state of my application by clicking the "Load Parties" button in the main menu.

## Phase 4: Task 2 ##
Sample Event Log:  
Sun Nov 26 23:47:01 PST 2023  
Added Dominic Eros to party Misters  
Sun Nov 26 23:47:01 PST 2023  
Added Connor Briggs to party Misters  
Sun Nov 26 23:47:01 PST 2023  
Added Kala Yavan to party Misters  
Sun Nov 26 23:47:01 PST 2023  
Added Morty to party The Beasts  
Sun Nov 26 23:47:26 PST 2023  
Connor Briggs selected  
Sun Nov 26 23:47:30 PST 2023  
Removed Kala Yavan from party Misters  
Sun Nov 26 23:47:32 PST 2023  
Dominic Eros selected  
Sun Nov 26 23:47:35 PST 2023  
Removed Dominic Eros from party Misters  

## Phase 4: Task 3 ##
I am pretty happy with my model package, though the interaction with themes could be streamlined, as right now Logos and Mythos simply extend theme and add no functionality themselves.
This is due to planned functionality down the line, but until that time they could be differentiated with a variable within Theme rather than as their own class.
The ui however could have many improvements. Generally I think there's some decoupling that could be done, as often the creation of a new panel is called from one class, but another has some necessary information to pass in.
There's also some very similar code between the PartyManager and CharacterManager class, so that could be refactored out into its own class. Specifically you'll note in the UML I have identically named private classes associated to each.
Those classes (SelectListener and DeleteListener) are not identical code but they are very close, so making that more reusable as its own class(es) would be beneficial.