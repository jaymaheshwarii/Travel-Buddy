package com.example.packyourbag.Data;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.example.packyourbag.Constants.MyConstants;
import com.example.packyourbag.Database.RoomDB;
import com.example.packyourbag.Models.Items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData extends Application {

    RoomDB database;
    String category;
    Context context;

    public static final String LAST_VERSION = "LAST_VERSION";
    public static final int NEW_VERSION = 1;

    public AppData(RoomDB database) {
        this.database = database;
    }

    public AppData(RoomDB database, Context context) {
        this.database = database;
        this.context = context;
    }

    public List<Items> getBasicData() {
        category = "Basic Needs";
        List<Items> basicItem = new ArrayList<Items>();
        basicItem.clear();
        basicItem.add(new Items("Visa", category, false));
        basicItem.add(new Items("Passport", category, false));
        basicItem.add(new Items("Tickets", category, false));
        basicItem.add(new Items("Wallet", category, false));
        basicItem.add(new Items("Driving License", category, false));
        basicItem.add(new Items("Currency", category, false));
        basicItem.add(new Items("House Key", category, false));
        basicItem.add(new Items("Books", category, false));
        basicItem.add(new Items("Travel Pillow", category, false));
        basicItem.add(new Items("Eye Patch", category, false));
        basicItem.add(new Items("Umbrella", category, false));
        basicItem.add(new Items("Note Book", category, false));
        basicItem.add(new Items("Pen", category, false));

        return basicItem;
    }

    public List<Items> getPersonalCareData() {
        String[] data = {"Tooth-Brush", "Tooth-Paste", "Floss", "MouthWash", "Shaving Cream",
                "Razor Blade", "hair Clip", "Moisturizer", "Cotton", "Nail Polish",
                "Scissors", "Creams", "Lotions", "Comb", "Soap", "Hair Oil", "Shampoo"
        };
        return prepareItemsList(MyConstants.PERSONAL_CARE_CAMEL_CASE, data);
    }

    public List<Items> getClothingData() {
        String[] data = {"Stockings", "UnderWear", "T-Shirt", "Pyjamas", "Casual Dress",
                "Vest", "Shirt", "Jeans", "Shorts", "Suits", "Sport Shoes",
                "Casual Shoes", "Gloves", "Rain Coat", "Belt", "Slippers"
        };
        return prepareItemsList(MyConstants.CLOTHING_CAMEL_CASE, data);
    }

    public List<Items> getBabyNeedsData() {
        String[] data = {"SnapSuit", "Outfits", "JumpSuit", "Baby Hat", "Baby Pyjama",
                "Baby Food", "Bottle", " Baby Brushes", "Baby Food", "Storage Container",
                "Baby Spoon", "Blankets", "Socks", "Gloves", "Diapers"
        };
        return prepareItemsList(MyConstants.BABY_NEEDS_CAMEL_CASE, data);
    }

    public List<Items> getHealthData() {
        String[] data = {"Aspirin", "Medicines", "Vitamins Used", "Lens Solutions",
                "Pain Killer", "First-Aid Kit", "Spray", "Hot Water Bag", "Adhesive Plaster"
        };
        return prepareItemsList(MyConstants.HEALTH_CAMEL_CASE, data);
    }

    public List<Items> getTechnologyData() {
        String[] data = {"Mobile Phone", "Phone Cover", "Camera", "USB Cable", "Camera Charger",
                "Laptop", "Mouse", "Speaker", "Headphones", "AirPods", "Power Bank", "Smart Watch",
                "Flash-Light", "SD Card"
        };
        return prepareItemsList(MyConstants.TECHNOLOGY_CAMEL_CASE, data);
    }

    public List<Items> getFoodData() {
        String[] data = {"Snacks", "Sandwich", "Chips", "Juice", "Tea Bags", "Coffee",
                "Water", "Thermos", "Baby Food"
        };
        return prepareItemsList(MyConstants.FOOD_CAMEL_CASE, data);
    }

    public List<Items> getBeachSuppliersData() {
        String[] data = {"Sea Glasses", "Sea Bed", "Suntan Cream", "Beach Umbrella",
                "Swim Fins", "Beach Towel", "Waterproof Clock", "Beach Slippers"
        };
        return prepareItemsList(MyConstants.BEACH_SUPPLIES_CAMEL_CASE, data);
    }

    public List<Items> getCarSuppliersData() {
        String[] data = {"Pump", "Car Jack", "Accident Record Set", "Car Cover", "Car Charger",
                "Window Sun Shades", "Spare Car Keys", "Auto Refrigerator"
        };
        return prepareItemsList(MyConstants.CAR_SUPPLIES_CAMEL_CASE, data);
    }

    public List<Items> getNeedsData() {
        String[] data = {"BackPacks", "Daily Bags", "Laundry Bag", "Sewing Kit",
                "Travel Lock", "Sports Equipments", "Magazine", "Luggage Tag"
        };
        return prepareItemsList(MyConstants.NEEDS_CAMEL_CASE, data);
    }


    public List<Items> prepareItemsList(String category, String[] data) {
        List<String> list = Arrays.asList(data);
        List<Items> dataList = new ArrayList<>();
        dataList.clear();

        for (int i = 0; i < list.size(); i++) {
            dataList.add(new Items(list.get(i), category, false));
        }
        return dataList;
    }

    public List<List<Items>> getAllData() {
        List<List<Items>> listOfAllItems = new ArrayList<>();
        listOfAllItems.clear();

        listOfAllItems.add(getBasicData());
        listOfAllItems.add(getClothingData());
        listOfAllItems.add(getPersonalCareData());
        listOfAllItems.add(getBabyNeedsData());
        listOfAllItems.add(getHealthData());
        listOfAllItems.add(getTechnologyData());
        listOfAllItems.add(getFoodData());
        listOfAllItems.add(getBeachSuppliersData());
        listOfAllItems.add(getCarSuppliersData());
        listOfAllItems.add(getNeedsData());

        return listOfAllItems;
    }

    public void persistAllData() {
        List<List<Items>> listOfAllItems = getAllData();

        for (List<Items> list : listOfAllItems) {
            for (Items items : list) {
                database.mainDao().saveItem(items);
            }
        }

        System.out.println("Data Added.");
    }

    public void persistDataByCategory(String category, Boolean onlyDelete) {
        try {
            List<Items> list = deleteAndGetListByCategory(category, onlyDelete);
            if (!onlyDelete) {
                for (Items item : list) {
                    database.mainDao().saveItem(item);
                }
                Toast.makeText(context,category + " Reset Successfully. ",Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(context, category + " Reset Successfully. ", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


    private List<Items> deleteAndGetListByCategory(String category, Boolean onlyDelete) {
        if (onlyDelete) {
            database.mainDao().deleteAllByCategoryAndAddedBy(category, MyConstants.SYSTEM_SMALL);
        } else {
            database.mainDao().deleteAllByCategory(category);
        }
        switch (category) {
            case MyConstants.BASIC_NEEDS_CAMEL_CASE:
                return getBasicData();

            case MyConstants.CLOTHING_CAMEL_CASE:
                return getClothingData();

            case MyConstants.PERSONAL_CARE_CAMEL_CASE:
                return getPersonalCareData();

            case MyConstants.BABY_NEEDS_CAMEL_CASE:
                return getBabyNeedsData();

            case MyConstants.HEALTH_CAMEL_CASE:
                return getHealthData();

            case MyConstants.TECHNOLOGY_CAMEL_CASE:
                return getTechnologyData();

            case MyConstants.FOOD_CAMEL_CASE:
                return getFoodData();

            case MyConstants.BEACH_SUPPLIES_CAMEL_CASE:
                return getBeachSuppliersData();

            case MyConstants.CAR_SUPPLIES_CAMEL_CASE:
                return getCarSuppliersData();

            case MyConstants.NEEDS_CAMEL_CASE:
                return getNeedsData();

            default:
                return new ArrayList<>();
        }
    }

}
