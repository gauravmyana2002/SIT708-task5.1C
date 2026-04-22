# Task 5.1 Media & Content Hub

This Android Studio project combines both mandatory subtasks from the Task 5.1 brief inside one single-activity application.

## Included Features

### Subtask 1: Sports News Feed App
- Single `MainActivity` with fragment-based navigation
- Horizontal RecyclerView for featured matches
- Vertical RecyclerView for latest sports news
- Detail fragment with large story image, description, and related stories
- Search/filter bar for sport categories and keywords
- Local bookmarks stored with Room and shown in a bookmarks fragment

### Subtask 2: iStream Personal Video Playlist App
- Login and sign-up screens
- User accounts stored in a local Room database
- Per-user playlist storage with isolated records for each account
- YouTube URL validation and playback inside a WebView using an embedded YouTube player
- Playlist screen with clickable saved URLs
- Logout flow that returns the user to the login screen

## Build

Use Android Studio to open the project folder and run the `app` module, or build from the command line:

```powershell
$env:JAVA_HOME='C:\Program Files\Android\Android Studio\jbr'
$env:Path='C:\Program Files\Android\Android Studio\jbr\bin;' + $env:Path
.\gradlew.bat assembleDebug
```

## Notes

- Sports content is hardcoded dummy data, as allowed by the brief.
- YouTube playback expects a valid public YouTube URL such as `https://www.youtube.com/watch?v=dQw4w9WgXcQ`.
