<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://tagutils" prefix="tg" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<div id="result-option-console" class="row option-console">
    <div class="span16">
        <h2 id="result-option-console-title" class="option-console-title">
            E-Accesible  <fmt:message key="result.display"/>
        </h2>
    </div>
    <div class="span16">
        <form:form  method="post" acceptCharset="UTF-8" enctype="application/x-www-form-urlencoded" class="form-stacked">
            View by E-Accessible label

            <div class="clearfix ">
                <label class="radiobutton" for="lvl1">
                    <input class="inputs-list" type="radio" id="lvl1" name="eaccess_lvl"  />
                    Niveau 1
                </label>

                <label class="radiobutton" for="lvl2">
                    <input class="inputs-list" type="radio" id="lvl2" name="eaccess_lvl" />
                    Niveau 2
                </label>

                <label class="radiobutton" for="lvl3">
                    <input class="inputs-list" type="radio" id="lvl3" name="eaccess_lvl" checked />
                    Niveau 3
                </label>
                <label class="radiobutton" for="lvl5">
                    <input class="inputs-list" type="radio" id="lvl5" name="eaccess_lvl" />
                    Niveau 5
                </label>
            </div>

            <div  class="actions option-console-update">
                <input type="submit" class="update-action" value="<fmt:message key="pageList.update"/>"/>
            </div> <!-- class="actions"-->


            <div class="clearfix ">
                <p> Le niveau 1  ça peut aller!</p>
                <p> Le niveau 2  ça va!</p>
                <p> Le niveau 3  c'est bien!</p>
                <p> Le niveau 5  c'est top!</p>
            </div>




        </form:form>
    </div>
</div>



