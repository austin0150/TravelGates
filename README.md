# TravelGates
  
## Description  
The purpose of this mod is to allow players to create a network of teleportation pads that let them travel long distances very quickly.  
Players are able to use the custom UI on each gate to configure it with an ID and a destination., as well as a blacklist/whitelist for gate access.  
The mod was built using the Forge library.
  
  
## Changelog  
### Version 1.0.6
#### Port to 1.16.4 
* No Feature Changes

### Version 1.0.6
#### (BugFixes)  
* Added translations for GUI and CreativeTab
* Added German Language

### Version 1.0.5  
#### (BugFixes)  
* Fixed TravelGates not functioning on Dedicated Server by adding in Data handling between the client and server.  
* Restructured client server system
* fixed some issues with the whitelist/blacklist not working right
  
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
  