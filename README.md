# Android Jetpack Compose Project

This is a native Android application built using **Kotlin** and **Jetpack Compose**. The app **Respiry** was developed by **Die Asthmanauten** and as part of a university project in collaboration with medical professionals to create a prototype for an App for the **Deutsche Atemwegsliga e.V.**. And was tested on an Emulated Android 16.0 (Balaclava) using a Pixel 9 emulator and two real Google Pixel phones.

## Description

The focus is on taking medication safely and regularly. To this end, the app offers a customisable **checklist** with a **reminder function**: **notifications** can be set up for each medication or task so that you never forget to take your medication.
A particular strength of the app is its clear **inhalation instructions**, which are available in both easy-to-understand text and video formats for various inhalers.
In addition, the app includes other asthma management features, such as:
- the ability to regularly enter **peak flow** values and have them automatically displayed graphically
- an **asthma control test** to assess current asthma control
- and a location-based forecast of **air quality** and **pollen count**

## 🚀 Getting Started

Follow these steps to run the project locally in Android Studio.

### Prerequisites

The App is known to run on

- Android Studio Merkat
- Android SDK min. 24, though we used 34

### Clone the Repository

```bash
git clone https://gitlab.mi.hdm-stuttgart.de/idp/ss25/die-asthmanauten.git
cd your-repo
```

### Open in Android Studio

1. Launch Android Studio.
2. Select **"Open an Existing Project"** and choose the cloned folder.
3. Wait for **Gradle sync** to complete. If it fails, try:
   - `File > Sync Project with Gradle Files`
   - `File > Invalidate Caches / Restart`

### Set Up Emulator

If you don’t have an emulator configured:
1. Go to **Tools > Device Manager**
2. Create a new **Pixel 9** emulator running **Android 16.0 (Balaclava)**
3. Start the emulator

### Run the App

- Click the **Run** button ▶️ in Android Studio
- Select the Pixel 9 emulator (or another compatible device)
- The app should launch automatically

## 📦 Technologies Used

- [Kotlin](https://kotlinlang.org/)
- [Jetpack Compose](https://developer.android.com/jetpack/compose)
- Android SDK

## 🧑‍💻 Authors (Die Asthmanauten)
- Adib Shaqaiq
- Berdan Cal
- Jonas Öhler
- Nils Friedrich
- Valentin Schiffner

## 📝 License

MIT License

Copyright (c) 2025 Die Asthmanauten

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

