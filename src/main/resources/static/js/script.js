window.document.onload = init();
window.addEventListener("resize", resize);
window.document.onresize = resize();

function init() {

    if(window.localStorage.getItem('sia_token')  == null) {
        alert('Please, insert a valid login.')
        window.location.href = '/login';
    }

    document.getElementById('inputtext').addEventListener('keyup', desplazarbarra, false);
    document.getElementById('botonAceptar').addEventListener('click',enviar,false);
    document.getElementById('botonBarra').addEventListener('click',enviar,false);
    document.getElementById('inputtext').addEventListener('keyup', function() {
        liveSearch(this.value);
        aceptarSearchBar();
        document.getElementById('sugerencias').style.display = "none";  
    }, false);

    document.getElementById('body').addEventListener('click', function() {
        document.getElementById('sugerencias').style.display = "none"; 
    }, false);

    $("img.imglogo").imgCheckbox();
    
    return this;
}

function desplazarbarra() {
    let input = document.getElementById('inputtext');

    if(input.value != '') {
        document.getElementById('logo').style.display = "none";    
        document.getElementById("input-container").classList.add('desplazar');
        document.getElementById('colinput').className = "col-12";
        document.getElementById("tablalogos").classList.remove('hidden');
        document.getElementById("tablalogos").classList.add('show');
        document.getElementById("radiogroup").classList.remove('hidden');
        document.getElementById("radiogroup").classList.add('show');
        document.getElementById("label-ordenar-por").classList.remove('hidden');
        document.getElementById("label-ordenar-por").classList.add('show');
        document.getElementById("button-aceptar").classList.remove('hidden');
        document.getElementById("button-aceptar").classList.add('show');
        document.getElementById("separdor-footer").classList.remove('hidden');
        document.getElementById("separdor-footer").classList.add('show');

        let sizescreen = window.screen.availWidth;

        if(sizescreen <= 1023) {
            document.getElementById("contenedor-input-productos").classList.add('separacion-input-productos-sm');
        }
    }
}

function enviar() {
    document.getElementById('cajamensajes').style.display = 'none';
    let elemProducto = document.getElementById('inputtext');
    let elemEmpresas = document.getElementsByClassName('imgChked');
    let elemOrdenarDown = document.getElementsByName('sizeBy');
    let valOrdenar = 1;
    let valProducto = '';
    let valEmpresas = '';

    document.getElementById('productos').innerHTML ='';
    document.getElementById('caja-logos-checked').innerHTML ='';

    if(elemProducto.value != '') {
        valProducto = encodeURIComponent(elemProducto.value);
    } else {
        return;
    }
      
    if(elemEmpresas.length != 0) {

        for (let item of elemEmpresas) {
            let didEmpresa = item.firstChild.alt;
            let sizescreen = window.screen.availWidth;
            let imgLogo;
            if(sizescreen > 1023) {
                imgLogo = imagenLogoEmpresa(parseInt(didEmpresa)).replace('w-50','w-100');
            } else {
                imgLogo = imagenLogoEmpresa(parseInt(didEmpresa)).replace('w-50','');
            }
            let logoEmpresa = document.createRange().createContextualFragment(imgLogo);
            logoEmpresa.children[0].classList.add('cardinal');
            logoEmpresa.children[0].classList.remove('climg');
            logoEmpresa.children[0].classList.remove('pt-2');
            logoEmpresa.children[0].classList.remove('pl-2');
            logoEmpresa.children[0].classList.remove('pb-2');
            let div = document.createElement("div");
            div.classList.add('mt-3');
            div.classList.add('border');
            div.classList.add('col-lg-1');
            div.classList.add('col-md-2');
            div.classList.add('col-sm-3');
            div.classList.add('col-xs-3');
            div.classList.add('mobile-size');
            div.appendChild(logoEmpresa);
            document.getElementById('caja-logos-checked').appendChild(div);
            valEmpresas += didEmpresa + ',';
        }

        let div1 = document.createElement('div');
        div1.classList.add('col-2');
        div1.classList.add('mt-3');
        div1.classList.add('offset-lg-' + (12 - elemEmpresas.length-3)); 
        let selectGroup = document.createElement("select");
        selectGroup.addEventListener('change',function() {
            let opcion = this.options[this.selectedIndex].value;
            if(opcion == 1) {               
                elemOrdenarDown[0].checked = true;
            } else {
                elemOrdenarDown[1].checked = true;
            }
        }, false);
        selectGroup.setAttribute('id','select-top');
        selectGroup.setAttribute("class", "form-control");
        selectGroup.style.border = "solid 1px #ddd";
        selectGroup.classList.add('float-right'); 
        selectGroup.setAttribute('onchange','enviar();return false;');
        let opcionPrecio = document.createElement("option");
        opcionPrecio.text = 'Precio';
        opcionPrecio.setAttribute('value','1');
        opcionPrecio.setAttribute('id','precio');
        let opcionVolumen = document.createElement("option");
        opcionVolumen.text = 'Precio/Kilo';
        opcionVolumen.setAttribute('value','2');
        opcionVolumen.setAttribute('id','volumen');
        
        if(elemOrdenarDown[0].checked) {
            opcionPrecio.selected = "true";
        } else {
            opcionVolumen.selected = "true";
        }

        selectGroup.appendChild(opcionPrecio);
        selectGroup.appendChild(opcionVolumen);
        div1.appendChild(selectGroup);
        document.getElementById('caja-logos-checked').appendChild(div1);

        let div = document.createElement("div");
        div.classList.add('col-1');
        div.classList.add('mt-3');
        let buttonVolver = document.createElement("a");
        buttonVolver.setAttribute("href", "/main");
        buttonVolver.setAttribute("id", "boton-volver");
        buttonVolver.innerHTML = "Volver";
        buttonVolver.classList.add('btn');
        buttonVolver.classList.add('btn-outline-secondary');
        buttonVolver.style.border = "solid 1px #ddd";
        buttonVolver.classList.add('ml-5');
        buttonVolver.classList.add('float-right'); 
        div.appendChild(buttonVolver);
        document.getElementById('caja-logos-checked').appendChild(div);
        
        valEmpresas = valEmpresas.substr(0,valEmpresas.length - 1);  
    } else {
        return;
    }

    if(elemOrdenarDown[1].checked) {
        valOrdenar = 2;
    }

    traerProductos(valProducto, valOrdenar, valEmpresas);
}

function traerProductos(producto, ordenar, strEmpresas) {	


    let d = new Date();
    let bufferProductos = window.localStorage.getItem(producto + '|' + ordenar + '|' + strEmpresas + '|' + d.getDate() + '|' + window.localStorage.getItem('sia_token'));

    $.ajax({
    type: "GET",
    url: "/search?country=101&category=101&sort="+ ordenar +"&product="+producto + "&pipedcompanies=" + strEmpresas,
    dataType: "text",
    timeout: 600000,
    beforeSend: function(xhr) {
        xhr.setRequestHeader ("Authorization", "Bearer " + window.localStorage.getItem('sia_token'));
        $('#sugerencias').addClass('hidden');
        $('#cajamensajes').css("display","none");
        $('#productos').addClass("hidden");
        $("#rowempresas").css("display", "none");
        $("#separdor-footer").css("display", "none");
        $("#radiogroupid").css("display", "none");
        $("#colinput").css("margin-top", "1%");
        $('#input-container').removeClass("containerCL");
        $("#input-container").addClass("mt-2");
        $('#ruleta').removeClass('hidden');
        $('#ruleta').addClass('show');
        if(bufferProductos != undefined && bufferProductos != null) {
            $('#ruleta').addClass('hidden');
            $('#productos').removeClass("hidden");
            $('#productos').addClass("show");
            componerCartas(bufferProductos);
            return;
        }
    }
    }).done(function (data) {        
        $('#ruleta').addClass('hidden');
        $('#productos').removeClass("hidden");
        $('#productos').addClass("show");
        window.localStorage.setItem(producto + '|' + ordenar + '|' + strEmpresas + '|' + d.getDate() + '|' + window.localStorage.getItem('sia_token'),data);
        componerCartas(data);        
    }).fail(function (xhr, textStatus, errorThrown) {
        console.log(errorThrown+'\n'+status+'\n'+xhr.statusText);
        window.location.href = '/login'; 
    });
}

function liveSearch(keyword) {
	
	if(keyword === '' || keyword === undefined) {
        $('#sugerencias').css("display", "none");  
		return
	}
	
    $.ajax({
        type: "GET",
        url: "/product/" + keyword,
        dataType: "text",
        timeout: 600000,
        beforeSend: function(xhr){
            xhr.setRequestHeader ("Authorization", "Bearer " + window.localStorage.getItem('sia_token'));
            $('#inputtext').addClass('loading-live-search');
            $('#sugerencias').html('');
            $('#sugerencias').css("display", "none");             
        }
        }).done(function (data) {
			$('#inputtext').removeClass('loading-live-search');
			
			if(data === '[]') {
                $('#sugerencias').css("display", "none");  
				return '';
			}
			
			let datosJSON = jQuery.parseJSON(data);
			
			datosJSON.forEach(elem => {
				let div = document.createElement('div');
				div.classList.add('col-12');
				div.classList.add('divsugerencia');
				div.setAttribute('onclick','focoSerchBar(this);return false;');
				div.innerText = elem.nomProducto;
				$('#sugerencias').append(div);
			});
                         
            $('#sugerencias').css("display", "block"); 
            $('#sugerencias').find('.sugerencia').each(function() {
                this.classList.add('pb-2');
                this.setAttribute("onclick","focoSerchBar(this);return false;");
            });
   
        }).fail(function (xhr, textStatus, errorThrown) {
            console.log(errorThrown+'\n'+status+'\n'+xhr.statusText);
            window.location.href = '/login'; 
        });
}

function focoSerchBar(param) {
    document.getElementById('inputtext').value = param.innerText.trim();
    document.getElementById('sugerencias').style.display = "none";
    let elemEmpresas = document.getElementsByClassName('imgChked');

    if(elemEmpresas != undefined && elemEmpresas.length != 0) {
        enviar();
    }
}

function componerCartas(data) {
    let estructuraHTML = '';
    let contador = 0;
    
    if(data.includes("[]")) { 
        document.getElementById("cajamensajes").classList.remove("hidden"); 
        document.getElementById("cajamensajes").style.display = "block";
        return;
    } else { 
    	document.getElementById("cajamensajes").style.display = "none"; 
    }

    try {
        let datosJSON = jQuery.parseJSON(data);
        let contenedor = document.getElementById('productos');
        let row;
        
        datosJSON.forEach(element => {           

            if(contador === 0) {
                row = document.createElement("div");
                row.classList.add("row");
                row.classList.add("pb-3");
                contenedor.appendChild(row);
            }

            estructuraHTML = '<div class="col-md-6 col-lg-2 col-sm-12 mb-3 crd">';
            estructuraHTML += '<div class="card card-block crd h-100 showcard">';            
            estructuraHTML += '<h4 class="card-title">';
            estructuraHTML += imagenLogoEmpresa(parseInt(element.didEmpresa));
            estructuraHTML += colorIdentificador(element.identificador);
            estructuraHTML += '</h4>'
            estructuraHTML += '<a href="' + element.nomUrl + '" target="_blank" rel="noopener noreferrer">';
            estructuraHTML += '<img class="imgprod" src="' + element.imagen + '" alt=" Producto" />';
            estructuraHTML += '</a>';
            estructuraHTML += '<h5 class="card-title p-2">' + element.nomProducto + '</h5>';
            estructuraHTML += '<p class="text pl-2">Precio ' + element.precio + '<br />P.Kilo  ' + element.precioKilo + '</p>';     
            estructuraHTML += '</div></div>';

            let card = document.createRange().createContextualFragment(estructuraHTML);
            row.appendChild(card);            

            contador++;

            if(contador === 6) {
                contador = 0;
            }
        });  
    } catch(error) {
        document.getElementById("cajamensajes").style.display = "block";      
        console.log(error);
    }
}

function colorIdentificador(identificador) {
	
	let resultado;
	
	switch (parseInt(identificador)) {
	case 1:
		resultado = '<span class="ident-primero">' + identificador + '</span>';
		break;
	case 2:
		resultado = '<span class="ident-segundo">' + identificador + '</span>';
		break;
	case 3:
		resultado = '<span class="ident-tercero">' + identificador + '</span>';
		break;
	default:
		resultado = '<span class="ident-resto">' + identificador + '</span>';
		break;
	}
	
	return resultado;
}

function imagenLogoEmpresa(didEmpresa) {

    switch (didEmpresa) {
        case 101:
            return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/mercadona.svg" alt="mercadona" />';
        case 102:
            return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/lidl.png" alt="lidl" />';
        case 103:
            return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/hipercor.svg" alt="hipercor" />';
        case 105:            			
            return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/dia.svg" alt="dia" />';
         case 106:            			
             return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/ulabox.svg" alt="ulabox" />';
         case 107:            			
             return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/eroski.svg" alt="eroski" />'; 
         case 108: 			
             return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/alcampo.svg" alt="alcampo" />';
         case 109:	
            return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/caprabo.svg" alt="caprabo" />';
         case 104:	
             return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/carrefour.svg" alt="carrefour" />';
         case 110:	
             return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/condis.svg" alt="condis" />';
         case 111:		
             return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/elcorteingles.svg" alt="elcorteingles" />';
         case 114:		
             return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/Simply-market.png" alt="simply" />';
         case 116:		
             return '<img class="w-50 pt-2 pl-2 pb-2 climg sm-img" src="img/consum.svg" alt="consum" />';
    }
}

function aceptarSearchBar() {
    let elemProducto = document.getElementById('inputtext');

    elemProducto.addEventListener("keypress", function(event) {
        if (event.keyCode === 13) {
          event.preventDefault();
          document.getElementById("botonAceptar").click();
          document.getElementById("sugerencias").style.display = "none";
          $('#botonBarra').focus();
        }
      });
}

$(window).resize(function() {
    $( "#log" ).append( "<div>Handler for .resize() called.</div>" );
  });

  $('#logout').click(function() {
    localStorage.clear();
    window.location.href="/login";
  });

function resize() {
    let ancho = window.outerWidth;

    if(ancho <= 1023) {
        document.getElementById('rowempresas').classList.remove('ml-2');
        document.getElementById('radiogroupid').classList.remove('ml-2');
        document.getElementById('radiogroup').classList.remove('offset-1');
        document.getElementById('colinput').classList.add('pt-1');
        document.getElementById('colinput').classList.add('colinp');

        if(document.getElementById('boton-volver') != null) {
            document.getElementById('boton-volver').style.display = 'none';
        }

        if(document.getElementById('select-top') != null) {
            document.getElementById('select-top').style.display = 'none';
        }
        
        document.getElementById('productos').classList.remove('mt-4');
        document.getElementById('productos').classList.add('pt-4');
        document.getElementById('productos').classList.add('pl-5');
        document.getElementById('productos').classList.add('pr-5');

        let listaTd = document.getElementsByTagName('td');

        for (let index = 0; index < listaTd.length; index++) {
            listaTd[index].classList.remove('p-3');
            listaTd[index].classList.add('p-1');
            
        }

        let cajaLogoCheckedBorder = document.getElementsByClassName('border');

        for (let index = 0; index < cajaLogoCheckedBorder.length; index++) {
            cajaLogoCheckedBorder[index].classList.remove('col-1');
            cajaLogoCheckedBorder[index].classList.add('col-2');
        }

        let logoProducto = document.getElementsByClassName('climg');

        for (let index = 0; index < logoProducto.length; index++) {
            logoProducto[index].classList.remove('w-50');
            logoProducto[index].classList.add('w-25');
        } 
        
        let logochecked = document.querySelectorAll('#caja-logos-checked > div:nth-child(n) > img');

        for (let index = 0; index < logochecked.length; index++) {
            logochecked[index].classList.remove('w-50');
            logochecked[index].classList.remove('w-100');
            logochecked[index].classList.remove('climg');
            logochecked[index].classList.add('w-25');
        }

        let cardinal = document.getElementsByClassName('cardinal');

        for (let index = 0; index < cardinal.length; index++) {
            cardinal[index].classList.remove('w-50');
            cardinal[index].classList.remove('w-100');
            cardinal[index].classList.remove('w-75');
            cardinal[index].classList.add('w-25');        
        }

    } else {
        document.getElementById('rowempresas').classList.add('ml-2');
        document.getElementById('radiogroupid').classList.add('ml-2');
        document.getElementById('radiogroup').classList.add('offset-1');
        document.getElementById('colinput').classList.remove('pt-5');
        document.getElementById('colinput').classList.remove('colinp');

        if(document.getElementById('boton-volver') != null) {
            document.getElementById('boton-volver').style.display = 'block';
        }

        if(document.getElementById('select-top') != null) {
            document.getElementById('select-top').style.display = 'block';
        }
        
        let listaTd = document.getElementsByTagName('td');

        for (let index = 0; index < listaTd.length; index++) {
            listaTd[index].classList.remove('p-1');
            listaTd[index].classList.add('p-3');            
        }

        let cajaLogoCheckedBorder = document.getElementsByClassName('border');

        for (let index = 0; index < cajaLogoCheckedBorder.length; index++) {
            cajaLogoCheckedBorder[index].classList.remove('col-2');
            cajaLogoCheckedBorder[index].classList.add('col-1');
        }

        let logoProducto = document.getElementsByClassName('climg');

        for (let index = 0; index < logoProducto.length; index++) {
            logoProducto[index].classList.remove('w-25');
            logoProducto[index].classList.add('w-50');
        }

        let logochecked = document.querySelectorAll('#caja-logos-checked > div:nth-child(n) > img');

        for (let index = 0; index < logochecked.length; index++) {
            logochecked[index].classList.remove('w-50');
            logochecked[index].classList.remove('w-100');
            logochecked[index].classList.remove('climg');
            logochecked[index].classList.add('w-100');
        }
    }

    let element = document.getElementsByClassName('cardinal');

    for (let index = 0; index < element.length; index++) {
    	element[index].classList.remove('w-50');
    	element[index].classList.remove('w-75');
    	element[index].classList.add('w-100');
    }
    
    return this;
}