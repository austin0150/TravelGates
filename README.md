# TravelGates
  
## Description  
The purpose of this mod is to allow players to create a network of teleportation pads that let them travel long distances very quickly.  
Players are able to use the custom UI on each gate to configure it with an ID and a destination., as well as a blacklist/whitelist for gate access.  
The mod was built using the Forge library.
  
## Changelog  
### Version 1.0.5 - Alpha  
#### (BugFixes)  
* Testing fix for client gui issue  
  
### Version 1.0.4  
#### (BugFixes)  
* Fixed a bug that would cause accidental teleportation between gates because Gate and Quick Gates were not sharing the same delay timer  

### Version 1.0.3  
#### (BugFixes)  
* Fixed bug that would cause a rubber band effect and not allow teleportation from a Gate  

### Version 1.0.2  
#### (BugFixes)  
* Fixed Text rendered in gui being shifted up or down  
* Fixed bug that made it possible for two gates to have the same ID  
* Fixed bug that did not change the destination ID of gates when the ID of that destination was changed.  
* Fixed bug that would cause Gate data from previously played world to be transferred to a newly created world.  
#### (Misc)  
* Added more informational logging to help in issue awareness  
* Incresed time delay between teleportations  
* Cleaned up codebase a bit
  
### Version 1.0.1  
#### (BugFixes)  
* Added hardness and harvest information to the QuickGate block  
* Added more time in between messages being sent to player for stepping on a block that does not have access to it's destination.  
#### (Misc)  
* Increased hardness value of Gate blocks to slow down harvest speed.  
* Updated Mod documentation  
* Removed lots of debugging messages  
  
## Gates  
There are currently two types of gates in the mod: The "Gate" and the "Quick Gate".  
The only difference in usage between the two is that once you place the quick gate and configure it, you cannot change any of its configurations until it is harvested and placed again.  
The "Gate" can be configured after placing by right clicking on it.  
Quick Gates are emerald Green and Gates are diamond blue.  
  
## Recipes  
#### Gate  
![GATE RECIPE](/images/gate_crafting.png)
#### Quick Gate  
![QUICK GATE RECIPE](/images/quick_gate_crafting.png)  

## UI  
#### Main Screen  
The main UI screen gives the player the options to set the gate ID, select the destination of the gate, edit the arrival whitelist, set the arrival blacklist, and select if the whitelist is in use.  
The current gate ID and destination is visible at the top of the UI.
![GATE SCREEN](/images/gate_screen.png)  
  
#### Gate ID Selection
Players are able to set the ID of the gate by entering the ID in the textbox, and then pressing accept.  
If a gate with that ID already exists, the ID will not be set and you will not be returned to the main screen.
![GATE ID SCREEN](/images/gate_id_selection_screen.png)  
  
#### Gate Destination Selection
Players can choose the destination of the gate by clicking on the gate ID that they want to travel to.  
The "Next" and "Back" buttons can be used to navigate and see more gate IDs.  
Pressing cancel will return the player to the main screen and not change the destination.
![DESTINATION SCREEN](/images/destination_selection_screen.png)
![DESTINATION SCREEN 2](/images/destination_selection_screen_2.png)  
  
#### Editing WhiteList  
The player can edit the incoming whitelist of the gate, meaning that selected IDs will be allowed to travel to the current gate.  
The player can select IDs to put on the whitelist by clicking the checkbox.  
A note, as soon as the checkbox is selected or removed, the ID is added or removed from the whitelist.  
Just like the destination selection, the "Next" and "Back" buttons can be used to navigate through pages of IDs.
![LIST SCREEN](/images/list_edit_screen.png)  
![LIST SCREEN 2](/images/list_edit_screen_2.png)  
  
#### Editing Blacklist  
The blacklist is active by default and IDs placed on this list will not be allowed to access the gate.  
Like the Whitelist, IDs are added by checking their checkbox. Navigation is also the same.  
  
#### Activating the Whitelist  
The Whitelist can be activated by cheking the checkbox on the main screen. Once the checkbox is selected, the Whitelist will become active and the Blacklist will be ignored.
![WHITELIST CHECKBOX](/images/whitelist_check_screen.png) 
