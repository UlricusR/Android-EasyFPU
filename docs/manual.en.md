# User Manual

## Installation

Get the app from the Google Play Store:

[![Google Play Store](assets/images/googleplay.png){ style="width:200px", align=left, .off-glb }](https://play.google.com/store/apps/details?id=info.rueth.fpucalculator){:target="_blank"}

## The Main Screen with the Food List

![The main screen with the food list](assets/images/MainActivity.png){style="width:80px", align=left }

The main screen appears when opening the app and contains a list of food items. It will be empty after first installation - after updating the app, the existing list will be taken over.

A food item in the sense of this app represents one component of a meal, which is „homogenous“ in itself. Several food items make up a meal. For example: A Schnitzel with French Fries and Ketchup (the meal) consists of the Schnitzel, the Fries and Ketchup (the food items).

The food list on the main screen is sorted in alphabetical order. It is recommended to assign unique names to each food item and not repeat existing names, although the app won‘t hinder you to do so.

![The search field](assets/images/MainActivity_search.png){style="width:80px", align=right }
![The food list filtered by favorites](assets/images/MainActivity_fav.png){style="width:80px", align=right }

When clicking the small star in the top right corner of the food list, only food items marked as favorites will be displayed. Favorites can either be defined in the Edit Food dialog or via the context menu of each food item. Furthermore you may search for food using the search field (any string in the food name).

## Adding new Food Items

![Add a new food item](assets/images/NewFoodActivity.png){style="width:80px", align=left }

Tapping the grey Plus symbol on the main screen opens a dialog to add a new food item.

The following entries are mandatory:

- Name: The (unique) name of the food item. 
- Favorite: Whether or not the food will be displayed in the list of favorite food items. 
- Calories per 100g: The calories of the food per 100g in kcal. 
- Carbs per 100g: The carbs per 100g of the food in grams. 

Optionally you can define up to three typical amounts for each food item, and you can comment each of them. The respective comment will later be displayed in the dialog to define a new meal and allows users to easily select a certain amount. Typical amounts can be used for defined food sizes and ease the use especially for children.

After saving a new food item, it will be displayed in the food list (sorted by alphabet).

## Calculation of a Meal

![New Meal](assets/images/NewMealActivity.png){style="width:80px", align=right }

A meal consists of at least one food item. To calculate a meal, select at least one food item from the food list on the main screen and tap the magenta colored Meal button with the cutlery symbol. A dialog appears to enter the amounts.

If typical amounts have been defined while adding the food item, these can now be selected for each food item. Alternatively you can enter your own food amount. These amounts should represent the actually consumed amount.

After all amounts have been entered, tap the arrow symbol to start the calculation.

![Resulting values of the complete meal and all individual food items](assets/images/CalcMealActivity.png){style="width:80px", align=left }

The following screen shows the resulting values of the total meal (on top in yellow) and of all individual food items. These are:

- The amount in grams
- The carbs in grams
- The Fat-Protein-Units (FPUs)
- The extended carbs in grams - equals to FPUs multiplied by 10
- The absorption time in hours

Modern Type 1 Diabetes insulin pumps allow entering FPUs or extended carbs (depends on the pump) as delayed bolus over the absorption time. This reduces late blood glucose increase caused by many FPUs (e.g. for Pizza).

## The Context Menu of a Food Item

![Context menu of a food item](assets/images/FoodListContextMenu.png){style="width:80px", align=right }

Tapping the three vertical dots of a food item opens the context menu. The following options are available:

- Set as favorite / Remove from favorites: Adds / removes the food item to / from the favorites
- Edit: Opens the dialog to edit the food item - the dialog is the same as for adding new food
- Delete: Deletes the food item from the list

## The Main Menu and the Settings

![The main menu](assets/images/MainMenu.png){style="width:80px", align=left }

Opening the main menu using the three vertical dots in the top right corner of the main screen gives you the following options:

- Settings: Opens the settings dialog (see below)
- About: Information about the app and a link to this documentation

![Settings screen](assets/images/SettingsScreen.png){style="width:80px", align=right }

The following setting groups are available:

Absorption Scheme:

- Edit absorption scheme: Opens the dialog to edit the absorption scheme
- Maximum FPUs: The maximum amount of Fat-Protein-Units that can be selected for the absorption scheme
- Maximum absorption time: The maximum absorption time that can be selected for the absorption scheme

Database:

- Export as JSON: Exports the database in human readable JSON format for exchange or backup
- Import from JSON: Imports the database from a JSON file
- Export raw database: Opens a dialog to export the app database in raw format (not recommended)

## The Absorption Scheme

The absorption scheme defines, which absorption time is proposed for a given amount of FPUs. The pre-defined absorption scheme matches today‘s recommendations from nutrition science and is as follows:

- 1 FPU (= 10g extended carbs): 3 hours absorption time
- 2 FPUs (= 20g extended carbs): 4 hours absorption time
- 3 FPUs (= 30g extended carbs): 5 hours absorption time
- 4 FPUs (= 40g extended carbs): 6 hours absorption time
- 6 FPUs (= 60g extended carbs) and more: 8 hours absorption time

![Edit the absorption scheme](assets/images/EditAbsorptionschemeActivity.png){style="width:80px", align=left }

The calculation logic is as follows:

Step 1: Calculation of the total calories, e.g. for 72g Chicken McNuggets (249 kcal per 100g): 72g * 249 kcal / 100g = 179 kcal

Step 2: Calculation of the calories caused by carbs (4 kcal per 1g of carbs), e.g. for 72g Chicken McNuggets (17g carbs per 100g): 72g * 17gCarbs / 100g * 4 kcal/gCarbs = 49 kcal

Step 3: Substract the calories caused by carbs from the total calories, e.g.: 179 kcal - 49 kcal = 130 kcal

Step 4: As 100 kcal represent 1 FPU, this results in 1.3 FPUs respectively 13g of extended carbs.

Step 5: The FPUs will be rounded, in our example to 1 FPU, and the absorption time will be looked up, in our example 3 hours.

Editing the absorption scheme is only recommended for advanced users, who have experience in FPUs / absorption time. The absorption scheme can always be reset to the pre-defined scheme above.

## Sample Data

You may download [this JSON file](assets/fpu_calculator_database.json) with some sample food items (food names are in German only). Import it via the main menu (Import database from JSON).

However, I strongly recommend to add your own food items, as e.g. typical amounts vary from person to person.
