/*
 * Copyright 2010, Wen Pu (dexterpu at gmail dot com)
 * Dual licensed under the MIT or GPL Version 2 licenses.
 * http://jquery.org/license
 *
 * Check out http://www.cs.illinois.edu/homes/wenpu1/chatbox.html for document
 *
 * Depends on jquery.ui.core, jquery.ui.widiget, jquery.ui.effect
 *
 * Also uses some styles for jquery.ui.dialog
 *
 */


// TODO: implement destroy()

function ValidURL(str) {
  var regex = /(http|https):\/\/(\w+:{0,1}\w*)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%!\-\/]))?/;
  if(!regex .test(str)) {
    return false;
  } else {
    return true;
  }
}
function Question(quest){
	tab = quest.split(" "); 
	//alert(tab[0]+" question "); 
	switch(tab[0]){
		case 'Désolé':{
			console.log("desole"); 
			return true ; 
			break; 
		} 
		default:{
			return false; 
		}
	}
}
(function($) {
	
    $.widget("ui.chatbox", {
        options: {
            id: null, //id for the DOM element
            title: null, // title of the chatbox
            user: null, // can be anything associated with this chatbox
            hidden: false,
            offset: 0, // relative to right edge of the browser window
            width: 300, // width of the chatbox
            messageSent: function(id, user, msg) {
                // override this
                this.boxManager.addMsg(user.first_name, msg);
            },
            boxClosed: function(id) {
            	$("#formchat").show(); 
      		  setTimeout(function() {
      			     $("#error_message").fadeOut(1200);
      			  },3000);
            	//alert("close"); 
            }, // called when the close icon is clicked
            boxManager: {
                // thanks to the widget factory facility
                // similar to http://alexsexton.com/?p=51
                init: function(elem) {
                    this.elem = elem;
                },
                addMsg: function(peer, msg, bot=false) {
                    var self = this;
                    var box = self.elem.uiChatboxLog;
                    var e = document.createElement('div');
                    
                    box.append(e);
                    //$(e).hide();
                    $(e).show();
                    $(e).addClass("ui-chatbox-msg");
                    $(e).css("maxWidth", $(box).width());
                    
                    var systemMessage = false;

                    if (peer  && bot == false) {
                        var peerName = document.createElement("b");
                        $(peerName).text(peer + ": ");
                        e.appendChild(peerName);
                    } else {
                    	if (peer  && bot == true) {
                            var peerName = document.createElement("b");
                            $(peerName).text(peer + ": ");
                            e.appendChild(peerName);
                        }
                        systemMessage = true;
                    }

                    var msgElement = document.createElement( systemMessage ? "i" : "span" );
                   
                    Question(msg); 
                    
            		if(ValidURL(msg)){
            			var parser = document.createElement('a');
            			var linkText = document.createTextNode(msg);
            			parser.appendChild(linkText);
            			parser.href = msg;
            			parser.setAttribute('target', '_blank');
            			e.appendChild(parser);
            			if(Question(msg) == true){
                  			// yes
                  			var buttonyes = document.createElement('div'); 
                  			buttonyes.appendChild(document.createTextNode("Oui"))
                  			buttonyes.setAttribute('id', 'answeryes');
                  			buttonyes.setAttribute('value', 'yes');
                  			buttonyes.setAttribute('style', 'margin-left:4px;margin-right: 4px');
                  			buttonyes.setAttribute("class","btn btn-success btn-xs");
                  			
                  			// no
                  			var buttonno = document.createElement('div'); 
                  			buttonno.appendChild(document.createTextNode("Non"))
                  			buttonno.setAttribute('id', 'answerno');
                  			buttonno.setAttribute('value', 'no');
                  			buttonno.setAttribute("class","btn btn-info btn-xs");
                  			
                  			e.appendChild(buttonyes);
                   			e.appendChild(buttonno);
                  		}
            		}else{
            			 $(msgElement).text(msg);
            			 e.appendChild(msgElement);
            			 if(Question(msg) == true){
            				// yes
            				 var buttonyes = document.createElement('div'); 
                   			buttonyes.appendChild(document.createTextNode("Oui"))
                   			buttonyes.setAttribute('id', 'answeryes');
                   			buttonyes.setAttribute('value', 'yes');
                   			buttonyes.setAttribute('style', 'margin-left:4px; margin-right: 4px');
                   			buttonyes.setAttribute("class","btn btn-success btn-xs");
                   			
                   		// no
                  			var buttonno = document.createElement('div'); 
                  			buttonno.appendChild(document.createTextNode("Non"));
                  			buttonno.setAttribute('id', 'answerno');
                  			buttonno.setAttribute('value', 'no');
                  			buttonno.setAttribute("class","btn btn-warning btn-xs");
            
                   			e.appendChild(buttonyes);
                   			e.appendChild(buttonno);
                    		
                   
                 		}else{
                 			if(peer === 'Bot'){
                 				var div = document.createElement('div'); 
                     			div.setAttribute('style', 'padding-top:2px;border-top:solid 1px black;  font-style:italic; font-size: 85%;');
                     			var textsatisfy = document.createTextNode("Satisfaites de la reponse? :");
                     		// OK
                     			var bok = document.createElement('button'); 
                     			bok.setAttribute('id', 'answerok');
                     			bok.setAttribute('style', 'button'); 
                     			bok.setAttribute("class","btn btn-success btn-xs");
                     			bok.setAttribute('style', 'margin-left:4px; margin-right: 5px');
                     			var buttonok = document.createElement('span'); 
                      			buttonok.setAttribute('value', 'ok');
                      			buttonok.setAttribute("class","glyphicon glyphicon-ok");
                      			bok.appendChild(buttonok);
                      			
                      		// KO
                      			var bko = document.createElement('button'); 
                      			bko.setAttribute('id', 'answerko');
                     			bko.setAttribute('style', 'button'); 
                     			bko.setAttribute("class","btn btn-warning btn-xs");
                     			var buttonko = document.createElement('span'); 
                     			buttonko.setAttribute('value', 'ko');
                     			buttonko.setAttribute("class"," glyphicon glyphicon-remove");
                     			bko.appendChild(buttonko);
                     			
                     			div.appendChild(textsatisfy); 
                      			div.appendChild(bok);
                      			div.appendChild(bko);
                      			e.appendChild(div);
                 			}
                 			
                 		}
            			 
            		}
            		$("button#answerok").on('click',function(event, ui) {
            			//alert("ok"); 
          				 answer = {
          						 	"answer":1
          				 			}; 
          				
   	       				 $.post(
   	                     		"api/bot/answerokko",
   	                             answer,
   	                     	    function(data, status){
   	                     	      // alert(status); 
   	                     	    }
   	                     ); 
   	       				 $(this).attr("disabled", true);
   	       				 $("button#answerko").hide(); 
          			});
            		$("button#answerko").on('click',function(event, ui) {
            			//alert("ko"); 
         				 answer = {
         						 	"answer":0
         				 			}; 
         				
  	       				 $.post(
  	                     		"api/bot/answerokko",
  	                             answer,
  	                     	    function(data, status){
  	                     			//alert(status); 
  	                     	    }
  	                     ); 
  	       				 $(this).attr("disabled", true);
  	       				 $("button#answerok").hide(); 
         			});
            		
            		
            		$("div#answeryes.btn.btn-success.btn-xs").on('click',function(event, ui) {
       				 console.log($(this).attr('value')); 
       				 answer = {
       						 	"answer":$(this).attr('value')
       				 			}; 
       				
	       				 $.post(
	                     		"api/bot/answeryesno",
	                             answer,
	                     	    function(data, status){
	                     	       // alert("Data: " + data + "\nStatus: " + status);
	                     	        console.log("yes"+data); 
	                     	        id = "Bot";  
	                     	        $("#chat_div").chatbox("option", "boxManager").addMsg(id, data, true);
	       
	                     	    }
	                     ); 
	       				 $(this).hide();
	       				$("div#answerno.btn.btn-warning.btn-xs").hide(); 
       			 	}); 
            		$("div#answerno.btn.btn-warning.btn-xs").on('click',function(event, ui) {
            			 answer = {
        						 	"answer":$(this).attr('value')
        				 			}; 
        				
 	       				 $.post(
 	                     		"api/bot/answeryesno",
 	                             answer,
 	                     	    function(data, status){
 	                     	       // alert("Data: " + data + "\nStatus: " + status);
 	                     	        console.log("data "+data); 
 	                     	        id = "Bot";  
 	                     	        $("#chat_div").chatbox("option", "boxManager").addMsg(id, data, true);
 	       
 	                     	    }
 	                     );
 	       				 $(this).hide();
 	       				 $("div#answeryes.btn.btn-success.btn-xs").hide(); 
          			 }); 
                    //$(msgElement).text(msg);
                   // e.appendChild(msgElement);
                    
                    $(e).fadeIn();
                    self._scrollToBottom();

                    if (!self.elem.uiChatboxTitlebar.hasClass("ui-state-focus")
                        && !self.highlightLock) {
                        self.highlightLock = true;
                        self.highlightBox();
                    }
                   
                },
                highlightBox: function() {
                    var self = this;
                    self.elem.uiChatboxTitlebar.effect("highlight", {}, 300);
                    self.elem.uiChatbox.effect("bounce", {times: 3}, 300, function() {
                        self.highlightLock = false;
                        self._scrollToBottom();
                    });
                },
                toggleBox: function() {
                    this.elem.uiChatbox.toggle();
                },
                _scrollToBottom: function() {
                    var box = this.elem.uiChatboxLog;
                    box.scrollTop(box.get(0).scrollHeight);
                }
            }
        },
        toggleContent: function(event) {
            this.uiChatboxContent.toggle();
            if (this.uiChatboxContent.is(":visible")) {
                this.uiChatboxInputBox.focus();
            }
        },
        widget: function() {
            return this.uiChatbox
        },
        _create: function() {
            var self = this,
            options = self.options,
            title = options.title || "No Title",
            // chatbox
            uiChatbox = (self.uiChatbox = $('<div></div>'))
                .appendTo(document.body)
                .addClass('ui-widget ' +
                          'ui-corner-top ' +
                          'ui-chatbox'
                         )
                .attr('outline', 0)
                .focusin(function() {
                    // ui-state-highlight is not really helpful here
                    //self.uiChatbox.removeClass('ui-state-highlight');
                    self.uiChatboxTitlebar.addClass('ui-state-focus');
                })
                .focusout(function() {
                    self.uiChatboxTitlebar.removeClass('ui-state-focus');
                }),
            // titlebar
            uiChatboxTitlebar = (self.uiChatboxTitlebar = $('<div></div>'))
                .addClass('ui-widget-header ' +
                          'ui-corner-top ' +
                          'ui-chatbox-titlebar ' +
                          'ui-dialog-header' // take advantage of dialog header style
                         )
                .click(function(event) {
                    self.toggleContent(event);
                })
                .appendTo(uiChatbox),
            uiChatboxTitle = (self.uiChatboxTitle = $('<span></span>'))
                .html(title)
                .appendTo(uiChatboxTitlebar),
            uiChatboxTitlebarClose = (self.uiChatboxTitlebarClose = $('<a href="#"></a>'))
                .addClass('ui-corner-all ' +
                          'ui-chatbox-icon '
                         )
                .attr('role', 'button')
                .hover(function() { uiChatboxTitlebarClose.addClass('ui-state-hover'); },
                       function() { uiChatboxTitlebarClose.removeClass('ui-state-hover'); })
                .click(function(event) {
                    uiChatbox.hide();
                    self.options.boxClosed(self.options.id);
                    return false;
                })
                .appendTo(uiChatboxTitlebar),
            uiChatboxTitlebarCloseText = $('<span></span>')
                .addClass('ui-icon ' +
                          'ui-icon-closethick')
                .text('close')
                .appendTo(uiChatboxTitlebarClose),
            uiChatboxTitlebarMinimize = (self.uiChatboxTitlebarMinimize = $('<a href="#"></a>'))
                .addClass('ui-corner-all ' +
                          'ui-chatbox-icon'
                         )
                .attr('role', 'button')
                .hover(function() { uiChatboxTitlebarMinimize.addClass('ui-state-hover'); },
                       function() { uiChatboxTitlebarMinimize.removeClass('ui-state-hover'); })
                .click(function(event) {
                    self.toggleContent(event);
                    return false;
                })
                .appendTo(uiChatboxTitlebar),
            uiChatboxTitlebarMinimizeText = $('<span></span>')
                .addClass('ui-icon ' +
                          'ui-icon-minusthick')
                .text('minimize')
                .appendTo(uiChatboxTitlebarMinimize),
            // content
            uiChatboxContent = (self.uiChatboxContent = $('<div></div>'))
                .addClass('ui-widget-content ' +
                          'ui-chatbox-content '
                         )
                .appendTo(uiChatbox),
            uiChatboxLog = (self.uiChatboxLog = self.element)
                .addClass('ui-widget-content ' +
                          'ui-chatbox-log'
                         )
                .appendTo(uiChatboxContent),
            uiChatboxInput = (self.uiChatboxInput = $('<div></div>'))
                .addClass('ui-widget-content ' +
                          'ui-chatbox-input'
                         )
                .click(function(event) {
                    // anything?
                })
                .appendTo(uiChatboxContent),
            uiChatboxInputBox = (self.uiChatboxInputBox = $("<textarea id='question' name='question'></textarea>"))
                .addClass('ui-widget-content ' +
                          'ui-chatbox-input-box ' +
                          'ui-corner-all'
                         )
                .appendTo(uiChatboxInput)
                .keydown(function(event) {
                    if (event.keyCode && event.keyCode == $.ui.keyCode.ENTER) {
                    	event.preventDefault();
                        msg = $.trim($(this).val());
                        if (msg.length > 0) {
                            self.options.messageSent(self.options.id, self.options.user, msg);
                        }
                        $(this).val('');
                        return false;
                    }
                })
                .focusin(function() {
                    uiChatboxInputBox.addClass('ui-chatbox-input-focus');
                    var box = $(this).parent().prev();
                    box.scrollTop(box.get(0).scrollHeight);
                })
                .focusout(function() {
                    uiChatboxInputBox.removeClass('ui-chatbox-input-focus');
                });

            // disable selection
            uiChatboxTitlebar.find('*').add(uiChatboxTitlebar).disableSelection();

            // switch focus to input box when whatever clicked
            uiChatboxContent.children().click(function() {
                // click on any children, set focus on input box
                self.uiChatboxInputBox.focus();
            });

            self._setWidth(self.options.width);
            self._position(self.options.offset);

            self.options.boxManager.init(self);

            if (!self.options.hidden) {
                uiChatbox.show();
            }
        },
        _setOption: function(option, value) {
            if (value != null) {
                switch (option) {
                case "hidden":
                    if (value)
                        this.uiChatbox.hide();
                    else
                        this.uiChatbox.show();
                    break;
                case "offset":
                    this._position(value);
                    break;
                case "width":
                    this._setWidth(value);
                    break;
                }
            }
            $.Widget.prototype._setOption.apply(this, arguments);
        },
        _setWidth: function(width) {
            this.uiChatboxTitlebar.width(width + "px");
            this.uiChatboxLog.width(width + "px");
            this.uiChatboxInput.css("maxWidth", width + "px");
            // padding:2, boarder:2, margin:5
            this.uiChatboxInputBox.css("width", (width - 18) + "px");
        },
        _position: function(offset) {
            this.uiChatbox.css("right", offset);
        }
    });
}(jQuery));
