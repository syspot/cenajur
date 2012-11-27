PrimeFaces.locales['pt_BR'] = {
        closeText: 'Fechar',
        prevText: 'Anterior',
        nextText: 'Pr&oacute;ximo',
        currentText: 'Come&ccedil;o',
        monthNames: ['Janeiro','Fevereiro','Mar&ccedil;o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Dez'],
        dayNames: ['Domingo','Segunda','Ter&ccedil;a','Quarta','Quinta','Sexta','S&aacute;bado'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S&aacute;b'],
        dayNamesMin: ['D','S','T','Q','Q','S','S'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'S&oacute; Horas',
        timeText: 'Tempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        currentText: 'Data Atual',
        ampm: false,
        month: 'M&ecirc;s',
        week: 'Semana',
        day: 'Dia',
        allDayText : 'Todo Dia'
    };

PrimeFaces.locales['en_US'] = {
        closeText: 'Fechar',
        prevText: 'Anterior',
        nextText: 'Pr&oacute;ximo',
        currentText: 'Come&ccedil;o',
        monthNames: ['Janeiro','Fevereiro','Mar&ccedil;o','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'],
        monthNamesShort: ['Jan','Fev','Mar','Abr','Mai','Jun', 'Jul','Ago','Set','Out','Nov','Dez'],
        dayNames: ['Domingo','Segunda','Ter&ccedil;a','Quarta','Quinta','Sexta','S&aacute;bado'],
        dayNamesShort: ['Dom','Seg','Ter','Qua','Qui','Sex','S&aacute;b'],
        dayNamesMin: ['D','S','T','Q','Q','S','S'],
        weekHeader: 'Semana',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'S&oacute; Horas',
        timeText: 'Tempo',
        hourText: 'Hora',
        minuteText: 'Minuto',
        secondText: 'Segundo',
        currentText: 'Data Atual',
        ampm: false,
        month: 'M&ecirc;s',
        week: 'Semana',
        day: 'Dia',
        allDayText : 'Todo Dia'
    };

function handleRequest(idDialog, widgerVar, args){
	if(args.validationFailed && !args.sucesso) {      	
		jQuery(idDialog).effect("shake", { times:3 }, 100);
	} else {  
		widgerVar.hide();
	}  

}

function handleRequestFaces(idDialog, widgerVar, args){
	if(args.validationFailed || !args.sucesso) {      	
		jQuery(idDialog).effect("shake", { times:3 }, 100);
	} else {  
		widgerVar.hide();
	}  
	
}

function handleRequestActionParteContraria(idDialog, widgerVar, args){
	if(args.validationFailed && !args.sucesso) {      	
		jQuery(idDialog).effect("shake", { times:3 }, 100);
	} else {  
		addParteContraria();
		widgerVar.hide();
	}  
	
}

function handleRequestActionParteContraria(idDialog, widgerVar, args){
	if(args.validationFailed && !args.sucesso) {      	
		jQuery(idDialog).effect("shake", { times:3 }, 100);
	} else {  
		addParteContraria2();
		widgerVar.hide();
	}  
	
}

function handleRequestActionCliente(idDialog, widgerVar, args){
	if(args.validationFailed && !args.sucesso) {      	
		jQuery(idDialog).effect("shake", { times:3 }, 100);
	} else {  
		addCliente();
		widgerVar.hide();
	}  
	
}

function handleRequestActionAgenda(idDialog, widgerVar, args){
	
	if(args.sucesso){
		
		if(args.criarAudiencia) {
			addAudiencia();
			widgerVar.hide();
		} else {  
			widgerVar.hide();
		}  
		
		if(args.imprimirFichaAtendimento){
			jQuery("#agendaColaboradorIdSubmit").val(jQuery("#agendaColaboradorId").val());
			dlgAgenda.hide();
			dlgImprimirFichaAtendimento.show();
		}
	}
	
}

function handleRequestFacesAtualizaAgenda(idDialog, widgerVar, args){
	if(args.validationFailed || !args.sucesso) {      	
		jQuery(idDialog).effect("shake", { times:3 }, 100);
	} else {  
		widgerVar.hide();
		myschedule.update();
	}  
	
}

function MascaraMoeda(objTextBox, SeparadorMilesimo, SeparadorDecimal, e){
    var sep = 0;
    var key = "";
    var i = j = 0;
    var len = len2 = 0;
    var strCheck = '0123456789';
    var aux = aux2 = '';
    var whichCode = (window.Event) ? e.which : e.keyCode;
    if (whichCode == 13) return true;
    key = String.fromCharCode(whichCode); // Valor para o c�digo da Chave
    if (strCheck.indexOf(key) == -1) return false; // Chave inv�lida
    len = objTextBox.value.length;
    for(i = 0; i < len; i++)
        if ((objTextBox.value.charAt(i) != '0') && (objTextBox.value.charAt(i) != SeparadorDecimal)) break;
    aux = '';
    for(; i < len; i++)
        if (strCheck.indexOf(objTextBox.value.charAt(i))!=-1) aux += objTextBox.value.charAt(i);
    aux += key;
    len = aux.length;
    if (len == 0) objTextBox.value = "";
    if (len == 1) objTextBox.value = "0"+ SeparadorDecimal + "0" + aux;
    if (len == 2) objTextBox.value = "0"+ SeparadorDecimal + aux;
    if (len > 2) {
        aux2 = "";
        for (j = 0, i = len - 3; i >= 0; i--) {
            if (j == 3) {
                aux2 += SeparadorMilesimo;
                j = 0;
            }
            aux2 += aux.charAt(i);
            j++;
        }
        objTextBox.value = "";
        len2 = aux2.length;
        for (i = len2 - 1; i >= 0; i--)
        objTextBox.value += aux2.charAt(i);
        objTextBox.value += SeparadorDecimal + aux.substr(len - 2, len);
    }
    return false;
}

function mascara(o,f){
    v_obj=o;
    v_fun=f;
    setTimeout("execMascara()",1);
}

function execMascara(){
    v_obj.value=v_fun(v_obj.value);
}

function integer(v){
    return v.replace(/\D/g,"");
}

function formatar(src, mask){
	
	var i = src.value.length;
	var saida = mask.substring(0,1);
	var texto = mask.substring(i);
	
	if (texto.substring(0,1) != saida) {
	    src.value += texto.substring(0,1);
	}
}