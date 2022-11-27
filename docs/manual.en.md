# User Manual

## Installation

Get the app from Apple's App Store:

[![Apple App Store](assets/images/appstore.png){ .off-glb }](https://apps.apple.com/de/app/easyfpu/id1529949520){:target="_blank"}

## The Main Screen with the Food List

![The main screen with the food list](assets/images/01_FoodList.png){style="width:100px", align=right }

The main screen appears when opening the app and contains a list of food items. It will be empty after first installation - after updating the app, the existing list will be taken over.

A food item in the sense of this app represents one component of a meal, which is „homogenous“ in itself. Several food items make up a meal. For example: A Schnitzel with French Fries and Ketchup (the meal) consists of the Schnitzel, the Fries and Ketchup (the food items).

The food list on the main screen is sorted in alphabetical order. It is recommended to assign unique names to each food item and not repeat existing names, although the app won‘t hinder you to do so.

![The food list filtered by favorites](assets/images/02_FoodList-filtered.png){style="width:100px", align=left }

When clicking the small star in the top right corner of the food list, only food items marked as favorites will be displayed. Favorites can be defined in the Edit Food dialog. Furthermore you may search for food using the search field (any string in the food name).

Long pressing a food item opens the dialog to edit the food item.

Swipe left to delete a food item.

## Adding new Food Items

Tapping the large green Plus symbol on top right corner of the main screen opens a dialog to add a new food item.

You have three ways how to add a new food item (Product or Ingredient):

### Option 1: Manual entry

![Add a new or edit a food item](assets/images/10_FoodEdit.png){style="width:100px", align=right }

The following entries are mandatory:

- Name: The (unique) name of the food item.
- Favorite: Whether or not the food will be displayed in the list of favorite food items
- Calories per 100g: The calories of the food per 100g in kcal
- Carbs per 100g: The carbs per 100g of the food in grams
- Thereof Sugars per 100g: The sugars per 100g as part of the carbs

Optionally you can define as many typical amounts as required for each food item, and you can comment each of them. The respective comment will later be displayed in the dialog to define a new meal and allows users to easily select a certain amount. Typical amounts can be used for defined food sizes and ease the use especially for children.

After saving a new food item, it will be displayed in the food list (sorted by alphabet).

### Option 2: Search in a Food Database

![The search results](assets/images/03_FoodSearch-results.png){style="width:100px", align=left }
![The detail view of a search or scan result](assets/images/03_FoodSearch-details.png){style="width:100px", align=left }

As of version 2.0.0, searching a food database is supported. To do so, enter a search term in the name field and hit the search icon.

The currently only available food database is [OpenFoodFacts](https://world.openfoodfacts.org/){:target=blank}. As OpenFoodFacts is an open food database, i.e. everybody can contribute just like in Wikipedia, please make sure that you cross-check the entries, as they might be wrong. You may use the details view for this, where pictures of the nutritional data will be displayed and can be zoomed.

### Option 3: Scan the Barcode of a Product

To scan a barcode, hit the scan icon beside the search icon and point your divice's camera towards the barcode of the product. If found, it will display the scan result view. Here you can decide whether or not to use the result.

If no scan is possible, simply wipe away the camera window from top to bottom.

## Composing a Meal

### Selection of Food Items

![Entering the consumed amount](assets/images/03_FoodSelect.png){style="width:100px", align=right }

A meal consists of at least one food item. To calculate a meal, start with selecting a food item from the food list on the main screen. A dialog appears to enter the consumed amount.

If typical amounts have been defined while adding the food item, these can now be selected. Alternatively you can enter your own consumed amount. This amount should represent the actually consumed amount.

Alternatively, you may use the green buttons to add the amount shown on the respective button to the actually entered amount.

In addition, you may store the actually entered amount as new typical amount. To do so, tap "Add to typical amounts", enter a comment and confirm by hitting the green Plus button.

Repeat these steps for each food item you want to add to your meal.

### The Meal Summary

![The e-carbs summary](assets/images/04_MealSummary.png){style="width:100px", align=left }

In case you have selected one or more food items, a hovering window will appear at the bottom of the screen. If you open this window by swiping it up from the bottom, you'll find the following information:

Red sugar icon: Carbs from sugars are usually the fastest to be absorbed. You can set the parameters in the Settings dialog.

Green hare icon: Regular carbs are absorbed slower than sugars. You may as well modify the parameters in the Settings dialog.

Blue tortoise icon: Extended carbs, aka. e-Carbs or Fake Carbs, do not stem from carbs, but from fat and proteins. That's why their absorption can take very long and starts late.
Here you can as well set the "Meal starts in x minutes" parameter, which normally is the waiting time between your insulin injection and the start of your meal.

You can clear a meal by tapping the red X symbol in the Meal Summary. Clearing a meal means that the amounts of all food items as well as the "Meal starts in x minutes" parameter will be set to zero.

### The Meal Details

![The meal details showing additional information](assets/images/08_MealDetails_full.png){style="width:100px", align=right }

Tapping the chevron icon in the summary will open the Meal Details view. You can verify which food items are part of your meal.

## Export to Apple Health

![The preview of carbs exported to Apple Health](assets/images/09_HealthExport.png){style="width:100px", align=left }

By tapping the Export button (the rectangle with the upward pointing arrow) you can export the calculated carbs as well as the total meal calories to Apple Health.

By toggling the switches, you can select or deselect the data to be exported to Apple Health.

Furthermore, you can set the "Meal starts in x minutes" parameter, which normally is the waiting time between your insulin injection and the start of your meal.

For carbs, a preview of data is generated representing your selection. It shows at which point in time which carbs portions of which type will be exported. Red bars represent sugars, green bars regular carbs, and blue bars e-carbs. Zoom in our out to see more or less details.

You may control the amount of "carbs chunks" via the interval parameters in the Settings dialog.

!!! warning "Important hints for Loopers"

    This feature might be useful for T1D patients using the Loop app to steer their insulin pump, as Loop can read the carbs exported from EasyFPU and consider those in the insulin therapy - if you allow Loop to read Apple Health data in the Apple Health settings. Do in this case not enter your meal a second time in Loop, as this will cause double entries and could lead to a low blood glucose.

    However, if you export carbs by mistake without actually consuming the exported amount, this may lead to a low or even urgent low blood glucose.

    Two safety features try to avoid this:

    - Before starting an export, EasyFPU will check when the last export happened. If this last export happened within the last x minutes, it will issue a warning and you will need to explicitely confirm the export a second time. The time period x can be configured in the Settings view.
    - As second confirmation before the actual export, you will be asked to authenticate - depending on your iOS settings by FaceID, TouchID or Code.

## Manage own recipes

As of version 2.0.0, it is possible to manage own recipes in EasyFPU. The following steps demonstrate this using the recipe of a marble cake.

### Step 1: Add Ingredients

![The list of Ingredients](assets/images/06_IngredientList.png){style="width:100px", align=right }

For composing a recipe, all ingredients need to be entered first. The ingredients can be found on the tab right beside the Products list. It is possible to move a food item from one list to the other using the context menu (longpressing the item).

Adding new ingredients is identical to adding new products: Either manually or using the food database search or scanning a barcode. The only difference is that the ingredient's category is set to "Ingredient", not to "Product".

### Step 2: Select the Ingredients of a Recipe

![The hovering window - wiped away](assets/images/07_ComposedProduct-min.png){style="width:100px", align=left }
![The hovering window with the summary of the recipe](assets/images/07_ComposedProduct-half.png){style="width:100px", align=left }

Just like selecting products for composing a meal, you need to select ingredients for composing a recipe. The difference is that the hovering window, which appears as soon as one ingredient has been selected, will display a summary of the recipe instead of the summary of a meal. You may wipe this hovering window away towards the bottom to have full access to the ingredients list.

If you enter a wrong amount, just type the ingredient a second time, which will remove it from the recipe. Then re-select it with the correct amount.

### Step 3: Enter Name, Total Weight and Number of Portions

![Entry of name, total weight and number of portions](assets/images/07_ComposedProduct-full.png){style="width:100px", align=right }

For the next important step, open the hovering window completely by wiping it up from the bottom. Enter the following data:

- The name of the recipe, e.g. "Marble Cake"
- The total weight of the composed product - this is important for the correct calculation of the nutritional values per 100g
- If you have enabled the generation of typical amounts, enter the number of portions you will cut out of your composed meal

### Step 4: Save the Product

![A non-matching total weight needs to be confirmed](assets/images/07_ComposedProduct-confirmweight.png){style="width:100px", align=left }

To save the product, just hit the "Save" button. In case the total weight differs from the sum of all ingredients, this needs to be explicitely confirmed.

### Step 5: Edit a Recipe

![The context menu of a product](assets/images/08_Product-context.png){style="width:100px", align=right }

To edit a recipe, longpress it in the Products list and select "Edit" in the context menu. If you want to keep the original recipe, please duplicate the product before editing it, otherwise the original recipe will be overwritten.

## The Main and the Context Menu

The Main Menu

Opening the main menu using the three vertical lines in the top left corner of the main screen gives you the following options:

![The main menu](assets/images/11_Menu.png){style="width:100px", align=left }

- Settings: Opens the dialog to edit the absorption schemes and further settings
- Import (format: JSON): Lets you import a food list - you can decide whether to append to or replace the existing food list
- Export (format: JSON): Exports the database in human readable JSON format for exchange or backup
- About: Information about the app
- Disclaimer: Opens the disclaimer for usage of the app
- Help on the Web: Link to this documentation

Swipe left or hit the X to close the menu.

The Context Menu

![The context menu of a product](assets/images/08_Product-context.png){style="width:100px", align=right }

The context menu of each product or ingredient opens by long-pressing it. You have the following options:

- Edit: Opens the dialog to edit the product or recipe
- Duplicate: Duplicates the product or the ingredient - the same name is used, but "- copy" is appended
- Share: Use this to share the product or the ingredient via AirDrop, iMessage, WhatsApp, Mail, etc. with other EasyFPU users
- Move to ingredients / products: Moves the product to the ingredients list or vice versa
- Delete: Deletes the product or ingredient

## The Absorption Scheme and further Settings

![Edit the absorption schemes and other settings](assets/images/12_Settings.png){style="width:100px", align=left }

The settings editor allows to set the values required for determining the absorption schemes for sugars, regular carbs and e-carbs.

Each absorption scheme consists of three parameters:

- Delay: The time required by your body to digest the respective carbs, i.e. after this time period they will impact your blood glucose.
- Absorption time: The time how long these carbs will - once started - impact your blood glucose (this parameter can be entered for sugars and regular carbs only, for e-carbs it will be calculated).
- Interval: This parameter is only required for exporting the carbs to Apple Health. The total amount of the respective carbs will be evenly distributed over the time period determined by the absorption time parameter, using this interval. Example: Given a 3h (=180min) absorption time and a 10min interval, an amount of 36g will be distributed in 18 chunks, 2g each: 36g / (180min / 10 min).

### Absorption Scheme for Sugars

You can choose whether or not to treat sugars separately from regular carbs. If you choose not to, sugars will be accounted for as regular carbs.

Sugars are usually absorbed within a relatively short period of time. Default setting is 2 hours with a delay of 0.

### Absorption Scheme for Regular Carbs

If you have chosen to treat sugars separately, they will be substracted from the regular carbs.

Regular carbs are usually absorbed slower than sugars. Default setting is 3 hours, starting with a delay of 5 minutes.

### Absorption Scheme for Extended Carbs

Extended carbs are not really carbs, but rather Fat-Protein-Units (FPUs), but they impact blood glucose just as if they were. As they stem from fat and proteins, which first need to be digested by your body, they will impact your blood glucose rather late (default setting is after 90 minutes).

The duration of impact is determined by the following absorption scheme, which matches today‘s recommendations from nutrition science:

- 1 FPU: 3 hours absorption time
- 2 FPUs: 4 hours absorption time
- 3 FPUs: 5 hours absorption time
- 4 FPUs: 6 hours absorption time
- 6 FPUs and more: 8 hours absorption time

This absorption scheme may also be modified.

By default, 1 FPU has a blood glucose impact comparable to 10g of carbs. This ratio can (and should!) be modified, as it is very individual. Especially for children it's recommended to start with a much lower carbs-per-FPU-ratio, e.g. 4 or 5 g/FPU.

The calculation algorithm for the FPU and e-carbs is as follows:

Step 1: Calculation of the total calories, e.g. for 72g Chicken McNuggets (249 kcal per 100g): 72g * 249 kcal / 100g = 179 kcal

Step 2: Calculation of the calories caused by carbs (4 kcal per 1g of carbs), e.g. for 72g Chicken McNuggets (17g carbs per 100g): 72g * 17gCarbs / 100g * 4 kcal/gCarbs = 49 kcal

Step 3: Substract the calories caused by carbs from the total calories, e.g.: 179 kcal - 49 kcal = 130 kcal

Step 4: As 100 kcal represent 1 FPU, this results in 1.3 FPUs, which could for example be 13 g e-carbs if the carbs-per-FPU-ratio was set to 10 g/FPU.

Step 5: The FPUs will be rounded, in our example to 1 FPU, and the absorption time will be looked up, in our example 3 hours.

Editing the absorption schemes is only recommended for advanced users, who have experience in FPUs / absorption time. All absorption schemes can always be reset to the pre-defined values.

### Alert Period between Apple Health Exports

In order to avoid double exports by mistake (potentially increasing the insulin delivery of your Loop system), EasyFPU will warn you in case you export data within a given period of time after the last export. This time period can be set here. Default is 15 minutes.

## Sample Data

You may download [this JSON file](assets/fpu_calculator_database.json) with some sample food items (food names and typical amounts are in German mainly). Import it via the main menu (Import database from JSON).

However, I strongly recommend to add your own food items, as e.g. typical amounts vary from person to person.
