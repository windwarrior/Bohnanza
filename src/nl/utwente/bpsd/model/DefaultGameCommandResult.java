/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.model;

public enum DefaultGameCommandResult {
    GAME_FINISHED,
    GAME_PROGRESS,
    GAME_HARVEST_ERROR,
    GAME_PLANT_ERROR, 
    SKIP,
    HARVEST, 
    PLANT, 
    DRAWN_TO_TRADING, 
    TRADE, 
    PLANT_TRADED, 
    DRAWN_TO_HAND
}
