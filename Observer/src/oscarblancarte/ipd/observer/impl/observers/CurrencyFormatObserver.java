/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oscarblancarte.ipd.observer.impl.observers;

import oscarblancarte.ipd.observer.impl.ConfigurationManager;
import oscarblancarte.ipd.observer.impl.IObserver;

/**
 *
 * @author ABUESTAN
 */
public class CurrencyFormatObserver implements IObserver {

    @Override
    public void notifyObserver(String command, Object source) {
        if(command.equals("currencyFormat")){
            ConfigurationManager conf = (ConfigurationManager)source;
            System.out.println("Observer ==> CurrencyFormatObserver.currencyFormatChange > " 
                    + conf.getCurrencyFormat());
        }
    }


}
