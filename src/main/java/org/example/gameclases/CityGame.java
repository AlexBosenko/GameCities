package org.example.gameclases;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CityGame extends Thread {
    private List<String> cities;
    private Player human;
    private Player comp;
    private String curCity;
    private String lastSymbol = "";
    private List<String> incorrectSymbol = Arrays.asList("И", "Й", "Ї", "Ь", "-", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", " ");

    public CityGame(Player human, Player comp, List<String> cities) {
        this.cities = cities;
        this.human = human;
        this.comp = comp;
    }

    private void setCurCity(String curCity) {
        this.curCity = curCity;
    }

    public boolean checkCity(String curCity) {
        String firstSymbol = curCity.substring(0, 1);
        if (cities.contains(curCity)
                && (firstSymbol.equals(getLastSymbol()) || getLastSymbol() == "")) {
            setCurCity(curCity);
            processHuman();
            return true;
        }
        return false;
    }

    public String getCurCity() {
        return curCity;
    }

    private void setLastSymbol(String cityName) {
        int index = 1;
        int length = cityName.length();
        String symbol = cityName.substring(length - index, length - index + 1).toUpperCase();
        while (incorrectSymbol.contains(symbol) && length - index >= 0) {
            index++;
            symbol = cityName.substring(length - index, length - index + 1).toUpperCase();
        }

        this.lastSymbol = symbol;
    }


    public String getLastSymbol() {
        return lastSymbol;
    }

    private void processHuman() {
        setLastSymbol(curCity);
        cities.remove(cities.indexOf(curCity));

        human.setCanMove(false);
        comp.setCanMove(true);
    }

    public void processComp() {
        List<String> collect = cities.stream()
                .filter(s -> s.toUpperCase().startsWith(lastSymbol.toUpperCase()))
                .collect(Collectors.toList());

        int size = collect.size();
        if (size > 0) {
            Random rnd = new Random();
            int index = rnd.nextInt(size);
            String randomCity = collect.get(index);
            System.out.println("Comp rndCity = " + randomCity);
            setCurCity(randomCity);
            setLastSymbol(randomCity);

            comp.setCanMove(false);
            human.setCanMove(true);
        }
    }

    public long checkStatus() {
        return cities.stream()
                .filter(str -> str.startsWith(lastSymbol))
                .count();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.currentThread().sleep(1500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            if (comp.isCanMove()) {
                processComp();
            }
        }
    }
}
