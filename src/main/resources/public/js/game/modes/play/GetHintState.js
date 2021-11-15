/**
 * This module exports the GetHintState class constructor.
 *
 * This component is an concrete implementation of a state
 * for the Game view; this state represents the state in which
 * the player is getting a hint.
 */
define(function(require){
    'use strict';

    // imports
    const PlayModeConstants = require('./PlayModeConstants');
    const AjaxUtils = require('../../util/AjaxUtils');

    /**
     * Constructor function.
     *
     * @param {PlayController} controller
     *    The Play mode controller object.
     */

    function GetHintState(controller){
        // Private attributes
        this._controller = controller;
    }

    //
    // Public (external) methods
    //

    /**
     * Get the name of this state.
     */
    GetHintState.prototype.getName = function getName() {
        return PlayModeConstants.HELP;
    }

    GetHintState.prototype.onEntry = function onEntry() {
        this._controller.disableButton(PlayModeConstants.HINT_BUTTON_ID);
        this._controller.disableButton(PlayModeConstants.BACKUP_BUTTON_ID);
        this._controller.disableButton(PlayModeConstants.SUBMIT_BUTTON_ID);
        this._controller.disableButton(PlayModeConstants.RESIGN_BUTTON_ID);
        AjaxUtils.callServer(
            // the action takes no data
            '/getHint',
            // the handler method should be run in the context of 'this' State object
            handleResponse, this);
    }

    function handleResponse(message) {
        // display the hint
        this._controller.displayMessage(message);
        var isTurnActive = this._controller.isTurnActive();
        if (isTurnActive) {
            this._controller.setState(PlayModeConstants.STABLE_TURN);
        } else {
            this._controller.setState(PlayModeConstants.EMPTY_TURN);
        }
    }

    // export class constructor
    return GetHintState;

});
