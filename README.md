# Dotify

Martin Zhang
An app that mimic spotify?! Supports Marshmellow or above

HW1 EC attempted:
- 1. A user is not allowed to apply a new username if the edit text field is empty. (+ 0.25)
- 2. Long pressing on the cover image changes the text color of the play count to a different color. (+ 0.25)
- 4. All hardcoded dimensions & colors are extracted into res/values/dimens.xml & res/values/colors.xml
respectively (+ .5)

HW1 progress: see screenshot in branch hw1


HW5: added notification feature to settings that once enabled, will push random new songs fetched from API in every 20 minutes
Note:
- activity folder: each file directly handle user's interaction on its associated activity screen
- fragment folder: where each mini entities live that has their own UI and lifecycle. Being linked through nav_graph
- manager folder: each specified in their own tasks and kinda like APIs and live in application level 
   - DotifyManager: determines what to do when user interact with the music portion of the App
   - RefreshSongManager: determines when to refresh (notifcation, fetch stuff)
   - SongSyncWorker: determine what to do when refresh
   - SongNotificationManager: determines what notification to send out

- Datarepository folder: where data can be interacted with (data either lies locally or fetch through retrofit from cloud)
- model folder: files to determine different data scheme used for gson
- SongDiffCallback: being used during recycleview shuffling to determine difference between old and new lists
- SongListAdapter: being used for recycleview to create more view holder, fit items into view holder, and update viewholders upon shuffling

HW5 EC attempted:
 - 1. push song and open that song in player activity
 - 2. Create another perioidic function that run every 2 days
