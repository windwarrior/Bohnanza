/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.utwente.bpsd.model.command;

import nl.utwente.bpsd.model.Game;
import nl.utwente.bpsd.model.DefaultGameCommandResult;

public interface Command {
    public DefaultGameCommandResult execute(Game g);
}
