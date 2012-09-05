/*
 * SimpleModal Basic Modal Dialog
 * http://www.ericmmartin.com/projects/simplemodal/
 * http://code.google.com/p/simplemodal/
 *
 * Copyright (c) 2010 Eric Martin - http://ericmmartin.com
 *
 * Licensed under the MIT license:
 *   http://www.opensource.org/licenses/mit-license.php
 *
 * Revision: $Id: basic.js 254 2010-07-23 05:14:44Z emartin24 $
 */

jQuery(function ($) {
	// Load dialog on page load
	//$('#basic-modal-content').modal();

	// Load dialog on click
	$('#addPatient').click(function (e) {
		$('#osx-modal-content-addImpPatient').modal();

		return false;
	});

	$('#delPatient').click(function (e) {
		$('#osx-modal-content-delImpPatient').modal();

		return false;
	});
	
	$('#delImpRelation').click(function (e) {
		$('#osx-modal-content-delImpRelation').modal();

		return false;
	});	

	$('#firmar').click(function (e) {
		$('#osx-modal-content-firmarEpisodio').modal();

		return false;
	});	
	
	$('#reabrir').click(function (e) {
		$('#osx-modal-content-reabrirEpisodio').modal();

		return false;
	});	

});