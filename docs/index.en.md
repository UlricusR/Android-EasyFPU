# What is EasyFPU about?

![EasyFPU app icon](assets/images/pizza_small.png){ align=left }

EasyFPU is an Android app - mainly for Type 1 Diabetes patients - to ease the calculation of carbs, fat-protein-units (FPUs) (aka extended carbs, e-carbs or fake carbs) and their matching absorption time.

## Disclaimer

!!! warning

    I need and want to emphasize that the use of this app is on your own risk!

The Fat-Protein-Units and absorption time calculated by this app are based on the recommendations of nutrition scientific articles, see list of links on this page. The app calculates FPUs by taking the total calories of a food item and subtracting the calories based on carbs (4 kcal/g). The difference represents the calories based on fat and proteins. For more details, please refer to the chapter on the absorption scheme.

!!! warning

    What you will do with this calculation, is completely up to you - you act on your own risk! Although I have thoroughly tested the calculation algorithm (and use it for a pump based insulin therapy in my own family), I refuse to guarantee its correctness.

Just a few hints from our personal experience:

- If you're looping, the loop loves to know if there are further carbs to be expected over time. It will then adjust its BG projection and - if activated by you - increase amount the and/or number of auto-boluses.
- If your auto-bolus settings limit the bolus amount for safety reasons, you may experience a high proposed manual bolus (e.g. 1.5 units at a BG of 180 mg/dl). Be careful before you really apply this, better reduce to half the amount in the beginning.
- In any case: Closely watch your BG when first starting to work with FPUs.

## Motivation and Concept

The motivation to program EasyFPU was to ease the calculation of Fat-Protein-Units especially for children with Type 1 Diabetes. Furthermore I was kept asking if I could provide the EasyFPU app, which is available for Android already for quite a while, for iOS. And as I've always been curious to learn a new programming language and system, here we go!

Although carbs are calculated as well, this can be done by many apps today. However, meals with a high portion of fat or protein lead to a late increase in blood glucose. Modern pump therapies can counter this by a delayed bolus. The calculation of the amount of FPUs and the corresponding absorption time is not trivial - and this is exactly what this app is doing.

The concept is simple: The user picks one or more food items from a list and enters their amount - optionally he can pick one of max. three pre-defined typical amounts. With this information the app will calculate FPUs.

Before being able to use the app, the user (or the user’s parents) need to invest a bit of time and effort to define the food items. The food list is deliberately empty when first installing the app, as every user has his own preferred food ([sample data](manual.md#sample-data) can be downloaded). You may limit your food list to food with a high fat or protein portion, like pizza, McDonals food, etc.

EasyFPU is also available for iOS. The iOS version is the newer one [with more features](https://ulricusr.github.io/#feature-overview), as I'm using it myself as part of an insulin therapy within my family.

## Privacy Policy

!!! note

    This section contains the privacy policy of the Android app. The privacy policy of this website can be [found here](https://ulricusr.github.io/legal/).

The Android App EasyFPU doesn't store any personal data, nor does it use any third party service that might store personal data.

The food list is stored locally on your smartphone and does not contain any personal data.

## Issues and Requests

[These issues](https://github.com/UlricusR/iOS-EasyFPU/issues){:target="_blank"} are known to me. Should you stumble over a new issue, please submit it via the same link.

As I'm not further developing EasyFPU for Android, I will only fix issues.

## Contribute

If you want to take over and further develop EasyFPU for Android, you're welcome! It's all Open Source and on [GitHub](https://github.com/UlricusR/Android-EasyFPU){:target="_blank"}.

Otherwise, just buy me a coffee - appreciated!

[![Buy me a coffee](assets/images/buymeacoffee_darkbackground.png#only-dark){ .off-glb }](https://www.buymeacoffee.com/ulricus){:target="_blank"}
[![Buy me a coffee](assets/images/buymeacoffee_lightbackground.png#only-light){ .off-glb }](https://www.buymeacoffee.com/ulricus){:target="_blank"}
