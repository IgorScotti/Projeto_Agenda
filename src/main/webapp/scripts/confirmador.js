/**
 * 
 */

 function confirmar(idcon){
	let resposta = confirm("Confirma a exclusão deste contato id:" + idcon)
	if(resposta === true){
		window.location.href = "delete?idcon="+idcon
	}
 }