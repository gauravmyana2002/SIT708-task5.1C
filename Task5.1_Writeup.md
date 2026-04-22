# Task 5.1 Media and Content Apps

## Student Details

- Name: `Your Name`
- Student ID: `Your Student ID`
- Unit: `SIT708 Mobile Systems Development`
- Task: `Task 5.1 Media & Content Apps`

## 1. LLM Declaration Statement

I used an AI large language model, OpenAI Codex, as a development support tool while completing this task.

The LLM assisted with:
- generating the initial Android project structure
- scaffolding fragments, adapters, Room database classes, and layouts
- refining UI text and layout styling
- helping troubleshoot YouTube playback and improve URL handling
- preparing this submission write-up template

All generated code was reviewed, tested, and adjusted within Android Studio. Final responsibility for the submitted work, testing, explanation, and demonstration remains with me.

LLM used:
- OpenAI Codex

Reason for use:
- to speed up scaffolding, debugging, and documentation while still manually reviewing the implementation

## 2. Project Overview

This submission combines both mandatory subtasks into one Android Studio project using a single activity and fragment-based navigation.

The application includes:
- a Sports News Feed App with featured matches, latest sports stories, search/filter support, related stories, and bookmarks
- an iStream Personal Video Playlist App with sign up, login, local Room database storage, playlist saving, and YouTube link playback support

## 3. Subtask 1: Sports News Feed App

### Objective

The aim of this subtask was to create a sports news application that demonstrates RecyclerView usage, fragment navigation, filtering, and local bookmarking.

### Implemented Features

- Single activity architecture using `MainActivity`
- Fragment-based navigation between landing, sports home, story detail, and bookmarks
- Horizontal RecyclerView for featured matches
- Vertical RecyclerView for latest sports news
- Story detail page with:
  - large image
  - title
  - description
  - related stories list
- Search/filter bar to filter sports items by keyword or category
- Bookmark functionality with local persistence
- Bookmarks screen to reopen saved stories later

### Design Notes

The sports app uses hardcoded dummy sports stories as allowed by the task brief. The interface was designed to visually separate featured content from the regular news feed while keeping the navigation simple and aligned with a single-activity approach.

## 4. Subtask 2: iStream Personal Video Playlist App

### Objective

The aim of this subtask was to build a media-focused app that demonstrates authentication, Room database usage, playlist management, and YouTube link playback.

### Implemented Features

- Login screen with username and password
- Sign up screen with:
  - full name
  - username
  - password
  - confirm password
- Local Room database for storing user credentials
- Per-user playlist storage so each user only sees their own saved content
- Home screen with:
  - YouTube URL field
  - Play button
  - Add to Playlist button
  - My Playlist button
  - Logout button
- Playlist screen showing saved links for the logged-in user
- Playlist items can be selected to reopen the content from the home screen
- Support for different YouTube link formats including standard watch links, short links, and playlist links

### YouTube Playback Note

Some YouTube videos or playlists cannot be played inside embedded players because of YouTube embed restrictions. The app includes an `Open in YouTube` fallback button so blocked content can still be opened externally when embedding is not allowed.

## 5. Architecture and Implementation Summary

The project was built using the following structure:

- `MainActivity` as the single activity host
- fragments for each screen
- RecyclerView adapters for sports stories and playlist items
- Room database for:
  - users
  - playlist items
  - bookmarks
- SharedPreferences-based session handling for login state
- WebView-based embedded YouTube player with fallback external opening

This structure was chosen to satisfy the assignment requirement for fragment-based navigation and to keep the project modular and easy to explain during the video demonstration.

## 6. Testing Summary

The following behaviours were tested during development:

- navigation between landing, sports, bookmarks, login, signup, home, and playlist screens
- creating a new account and logging in with saved credentials
- validating incorrect and incomplete login or sign-up input
- adding playlist entries for the active user
- confirming playlists remain separated between different users
- opening sports story details and saving/removing bookmarks
- filtering sports content by category text
- building the project successfully using `assembleDebug`

## 7. Challenges and Solutions

### Challenge 1: Managing two mandatory subtasks in one project

This was solved by using a single activity with fragments for all screens. This approach meets the sports app requirement and keeps the user flow consistent across the whole project.

### Challenge 2: Local user and playlist storage

This was solved by implementing a Room database with separate tables for users, playlist items, and bookmarks.

### Challenge 3: YouTube embed limitations

Some YouTube content produced embed restrictions inside the WebView player. To handle this gracefully, the app includes support for normalized URLs and an external open option when embedded playback is blocked.

## 8. How to Demonstrate the App in the Video

Suggested demonstration flow:

1. Show the landing screen and explain that both mandatory subtasks are included in one project.
2. Open the Sports News Feed App.
3. Show featured matches and latest sports news.
4. Use the filter/search field with categories such as Football or Cricket.
5. Open a story detail page and explain related stories.
6. Save a bookmark and show the bookmarks screen.
7. Return to the landing page and open iStream.
8. Create a user account, then log in.
9. Paste a YouTube link and explain playback handling.
10. Add the link to the playlist.
11. Open the playlist screen and show that the saved item belongs to the logged-in user.
12. Log out and explain how user-specific playlist storage works.

## 9. Required Submission Links

### YouTube Demonstration Video Link

`Paste your YouTube demo link here`

### GitHub Repository Link

`Paste your public GitHub repository link here`

### LLM Conversation Link

`Paste your LLM conversation/share link here`

## 10. Final Notes

Before submission, replace all placeholder values in this document and rename the final file using the required format from the task brief:

`Credit_Task5.1_YourName_StudentID.pdf`

If needed, this markdown can be copied into Word and exported as PDF after you insert your final links and personal details.
