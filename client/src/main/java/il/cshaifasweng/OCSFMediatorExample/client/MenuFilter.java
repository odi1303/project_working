package il.cshaifasweng.OCSFMediatorExample.client;


import java.util.List;

public class MenuFilter {
    private List<String> filteredBranches;
    private List<String> filteredIngredients;


    public MenuFilter(List<String> filteredBranches, List<String> filteredIngredients) {
        this.filteredBranches = filteredBranches;
        this.filteredIngredients = filteredIngredients;
    }
    public List<String> getFilteredBranches() {
        return filteredBranches;
    }
    public List<String> getFilteredIngredients() {
        return filteredIngredients;
    }

    public MenuClient filterMenu(MenuClient menu) {
        MenuClient filteredMenu = new MenuClient();
        if (filteredBranches == null || filteredIngredients == null || menu.getMenu() == null) {
            return filteredMenu;
        }
        for (DishClient dish : menu.getMenu()){
            boolean isAtListOneNotFilterAvailableBranch = dish.getAvailableBranches().stream().anyMatch(filteredBranches::contains);
            if (isAtListOneNotFilterAvailableBranch) {
                filteredMenu.addDish(dish);
            }
        }
        return filteredMenu;
    }
}
