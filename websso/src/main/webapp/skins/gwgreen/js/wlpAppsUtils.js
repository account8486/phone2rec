function fixIeFormButtons(thisButton)
{
    // IE sends all <button>s in a form, whereas <input> "buttons" it only sends the single one clicked.
    // This messes up netui's actionOverride scheme which was written w/ the single button return logic.
    // This function disables all the <button>s except for the one that was clicked.
    // Usage: <button onclick="fixIeFormButtons(this)" ....>Label</button>
    // Doesn't matter if <button> is type="submit" or type="button" since we check for a parent form.
    if(thisButton.form)
    {
        var formButtons = thisButton.form.getElementsByTagName("BUTTON");
        if(formButtons.length > 1)
        {
            for (i=0; i < formButtons.length; i++ )
            {
                // Store the current disabled setting so it cal be rolled back later if needed
                formButtons[i].previousDisabled = formButtons[i].disabled;
                // Disable every <button> that was not the one clicked
                if(thisButton != formButtons[i])
                {
                    formButtons[i].disabled = true;
                }
            }
        }
    }
}

/*
   name - name of the cookie
   value - value of the cookie
   [expires] - expiration date of the cookie
     (defaults to end of current session)
   [path] - path for which the cookie is valid
     (defaults to path of calling document)
   [domain] - domain for which the cookie is valid
     (defaults to domain of calling document)
   [secure] - Boolean value indicating if the cookie transmission requires
     a secure transmission
   * an argument defaults when it is assigned null as a placeholder
   * a null placeholder is not required for trailing omitted arguments
*/

function setCookieWlpApps(name, value, expires, path, domain, secure) {
  var curCookie = name + "=" + escape(value) +
      ((expires) ? "; expires=" + expires.toGMTString() : "") +
      ((path) ? "; path=" + path : "") +
      ((domain) ? "; domain=" + domain : "") +
      ((secure) ? "; secure" : "");
  document.cookie = curCookie;
}

/*
  name - name of the desired cookie
  return string containing value of specified cookie or null
  if cookie does not exist
*/

function getCookieWlpApps(name) {
  var dc = document.cookie;
  var prefix = name + "=";
  var begin = dc.indexOf("; " + prefix);
  if (begin == -1) {
    begin = dc.indexOf(prefix);
    if (begin != 0) return null;
  } else
    begin += 2;
  var end = document.cookie.indexOf(";", begin);
  if (end == -1)
    end = dc.length;
  return unescape(dc.substring(begin + prefix.length, end));
}

/*
   name - name of the cookie
   [path] - path of the cookie (must be same as path used to create cookie)
   [domain] - domain of the cookie (must be same as domain used to
     create cookie)
   path and domain default if assigned null or omitted if no explicit
     argument proceeds
*/

function deleteCookieWlpApps(name, path, domain) {
  if (getCookieWlpApps(name)) {
    document.cookie = name + "=" +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") +
    "; expires=Thu, 01-Jan-70 00:00:01 GMT";
  }
}

/**
 * The following functions are used with the selectAllCheckbox tag.
 */
function toggleCheckboxesWlpApps(aForm, newValue, selectAllCheckboxName, targetCheckboxName)
{
  var formElements = aForm.elements;
  
  for (var i=0; i<formElements.length; i++)
  {
    var element = formElements[i];

    if (element.type == 'checkbox')
    {
      if (element.name != selectAllCheckboxName)
      {
        if (null == targetCheckboxName)
        {
          element.checked = newValue;
          //addEventWlpApps(element, 'click', updatedCheckboxWlpApps);
        }
        else
        {
          if (element.name == targetCheckboxName)
          {
            element.checked = newValue;
	        //addEventWlpApps(element, 'click', updatedCheckboxWlpApps);
          }
        }
      }
    }        
  }
}

function selectAllCheckboxesWlpApps(selectAllCheckbox, targetCheckBoxName)
{
  var theForm = selectAllCheckbox.form;
  toggleCheckboxesWlpApps(theForm, selectAllCheckbox.checked, selectAllCheckbox.name, targetCheckBoxName);
  
}

function addEventWlpApps(obj, evType, fn){ 
	// First, detach event...
	if (obj.removeEventListener)
	{ 
		obj.removeEventListener(evType, fn, false); 
	}
	else if (obj.detachEvent)
	{ 
		var r = obj.detachEvent("on"+evType, fn); 
	}
	
	// Now, add it back in
	if (obj.addEventListener)
	{ 
		obj.addEventListener(evType, fn, false); 
	}
	else if (obj.attachEvent)
	{ 
		var r = obj.attachEvent("on"+evType, fn); 
	}
}

function updatedCheckboxWlpApps()
{
  alert("The checkbox that was clicked - Name:" + this.name + " Value: " + this.value);
}

