/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulation;

import java.util.HashSet;
import java.util.Set;
import utility.Point;

/**
 *
 * @author Kathryn Andrew
 */
public class BotTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("Creation failures: " + makeBotTest(true));
        
        System.out.println("Run failures: " + runBotTest(true));
        
    }
    
    public static int makeBotTest(boolean v) {
        int fails = 0;
        Set<Sense> senses = new HashSet<>();
        Set<Behaviour> behaviours = new HashSet<>();
        Point testPos = new Point (10,10);
        int testOutputs = 1;
        int testInputs = 1;
        double testEnergy = 10;
        GRep g = new GRep(10, 5, 1, 1);
        Bot b = new Bot(g, senses, behaviours, testEnergy, testPos);
        
        
        if (!g.equals(b.getGRep())) {
            fails++;
            if (v)
                System.out.println("Failure: bot genes not provided genes.");
        }
        if (!senses.equals(b.getSenses())) {
            fails++;
            if (v)
                System.out.println("Failure: bot senses not provided senses.");
        }
        if (!behaviours.equals(b.getBehaviours())) {
            fails++;
            if (v)
                System.out.println("Failure: bot behaviours not provided behaviours.");
        }     
        if (b.getOutputs().length != testOutputs) {
            fails++;
            if (v)
                System.out.println("Failure: output array wrong size. " + testOutputs + " expected, " + b.getOutputs().length + " found.");
        }    
        if (b.getInputs().length != testInputs) {
            fails++;
            if (v)
                System.out.println("Failure: input array wrong size. " + testInputs + " expected, " + b.getInputs().length + " found.");
        }        
        if (!testPos.equals(b.getPosition())) {
            fails++;
            if (v)
                System.out.println("Failure: bot position not set correctly. " + testPos + " expected, " + b.getPosition() + " found.");
        }
        if (testEnergy != b.getEnergy()) {
            fails++;
            if (v)
                System.out.println("Failure: bot energy not set correctly. " + testEnergy + " expected, " + b.getEnergy() + " found.");
        }        
        return fails;
    }
    
    
    public static int runBotTest(boolean v) {    
        int fails = 0;
        double senseVal = 100;
        Point behVal = new Point(15,15);
        Point startPoint = new Point(0,0);
        Set<Sense> senses = new HashSet<>();
        senses.add(new SenseDEBUG(senseVal));
        Set<Behaviour> behaviours = new HashSet<>();
        behaviours.add(new BehaviourDEBUG(behVal));
        int testOutputs = 1;
        int testInputs = 1;
        double testEnergy = 0;
        GRep g = new GRep(10, 5, testInputs, testOutputs);
        Bot b = new Bot(g, senses, behaviours, testEnergy, startPoint);        
        
        b.run();

        if (!b.getPosition().equals(startPoint)) {
            fails++;
            if (v)
                System.out.println("Debug behaviour failure: bot position changed with 0 energy.");
        }        
        
        b = new Bot(g, senses, behaviours, 10, startPoint);
        b.run();
        
        if (b.getEnergy() != 9) {
            fails++;
            if (v)
                System.out.println("Bot metabolism not running correctly. 9 energy expected, " + b.getEnergy() + " found.");            
        }
        
        if (!behVal.equals(b.getPosition())) {
            fails++;
            if (v)
                System.out.println("Debug behaviour failure: bot position not set correctly. " + behVal + " expected, " + b.getPosition() + " found.");
        }
        if (b.getInputs()[0] != senseVal) {
            fails++;
            if (v)
                System.out.println("Failure: bot input not set correctly. " + senseVal + " expected, " + b.getInputs()[0] + " found.");
        }        
        
        return fails;
        
    }
}
