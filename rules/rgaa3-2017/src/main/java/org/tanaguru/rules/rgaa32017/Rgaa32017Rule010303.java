/*
 * Tanaguru - Automated webpage assessment
 * Copyright (C) 2008-2017  Tanaguru.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Contact us by mail: tanaguru AT tanaguru DOT org
 */

package org.tanaguru.rules.rgaa32017;

import org.tanaguru.ruleimplementation.AbstractPageRuleWithSelectorAndCheckerImplementation;
import org.tanaguru.ruleimplementation.ElementHandler;
import org.tanaguru.ruleimplementation.ElementHandlerImpl;
import org.tanaguru.ruleimplementation.TestSolutionHandler;
import org.tanaguru.rules.elementchecker.ElementChecker;
import org.tanaguru.rules.elementchecker.pertinence.AttributePertinenceChecker;
import org.tanaguru.rules.elementselector.ImageElementSelector;
import org.tanaguru.rules.keystore.RemarkMessageStore;

import static org.tanaguru.entity.audit.TestSolution.FAILED;
import static org.tanaguru.entity.audit.TestSolution.NEED_MORE_INFO;
import static org.tanaguru.entity.audit.TestSolution.PASSED;
import static org.tanaguru.rules.keystore.AttributeStore.ALT_ATTR;
import static org.tanaguru.rules.keystore.AttributeStore.SRC_ATTR;
import static org.tanaguru.rules.keystore.AttributeStore.TITLE_ATTR;
import static org.tanaguru.rules.keystore.AttributeStore.ARIA_LABEL_ATTR;
import static org.tanaguru.rules.keystore.AttributeStore.ARIA_LABELLEDBY_ATTR;
import static org.tanaguru.rules.keystore.CssLikeQueryStore.FORM_BUTTON_WITH_ALT_CSS_LIKE_QUERY;
import static org.tanaguru.rules.keystore.RemarkMessageStore.*;

import java.util.Collections;

import javax.annotation.Nullable;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.nodes.Element;
import org.tanaguru.entity.audit.TestSolution;
import org.tanaguru.processor.SSPHandler;
import org.tanaguru.rules.textbuilder.TextAttributeOfElementBuilder;
import org.tanaguru.rules.textbuilder.TextElementBuilder;
import org.tanaguru.rules.elementchecker.CompositeChecker;
import org.tanaguru.rules.elementchecker.text.TextNotIdenticalToAttributeChecker;

/**
 * Implementation of the rule 1.3.3 of the referential Rgaa 3-2017.
 * <br/>
 * For more details about the implementation, refer to <a href="http://tanaguru-rules-rgaa3.readthedocs.org/en/latest/Rule-1-3-3">the rule 1.3.3 design page.</a>
 * @see <a href="http://references.modernisation.gouv.fr/referentiel-technique-0#test-1-3-3"> 1.3.3 rule specification</a>
 *
 */
public class Rgaa32017Rule010303 extends AbstractPageRuleWithSelectorAndCheckerImplementation {

    /** The name of the nomenclature that handles the image file extensions */
    private static final String IMAGE_FILE_EXTENSION_NOM = "ImageFileExtensions";
    
    private final ElementHandler<Element> titleElement = new ElementHandlerImpl();
    private final ElementHandler<Element> ariaLabelElement = new ElementHandlerImpl();
    private final ElementHandler<Element> ariaLabelledbyElement = new ElementHandlerImpl();


    /**
     * Constructor
     */
    public Rgaa32017Rule010303() {
    	super();       
        setElementSelector(new ImageElementSelector(FORM_BUTTON_WITH_ALT_CSS_LIKE_QUERY, true, false));
    }

    protected void select(SSPHandler sspHandler) {
    	super.select(sspHandler);
    	
    	    	
    	if(getElements() != null) {    

    		for (Element el : getElements().get()) {
    			    		    
    			if(el.toString().contains(TITLE_ATTR)) {
    				titleElement.add(el);
    			}
    			if(el.toString().contains(ARIA_LABELLEDBY_ATTR)) {
    				ariaLabelledbyElement.add(el);
    			}
  	    		if(el.toString().contains(ARIA_LABEL_ATTR) && !el.toString().contains(ARIA_LABELLEDBY_ATTR)) {
  	    			ariaLabelElement.add(el);
  	    		}
    		}
    	}
    }
    
    protected void check( SSPHandler sspHandler, TestSolutionHandler testSolutionHandler) {
    	
    	ElementChecker ec = 
    			new AttributePertinenceChecker(
	                ALT_ATTR,
	                // check emptiness
	                true,
	                // compare with src attribute
	                new TextAttributeOfElementBuilder(SRC_ATTR),
	                // compare attribute value with nomenclature
	                IMAGE_FILE_EXTENSION_NOM,
	                // not pertinent message
	                NOT_PERTINENT_ALT_MSG,
	                // manual check message
	                CHECK_ALT_PERTINENCE_OF_INFORMATIVE_IMG_MSG,
	                // evidence elements
	                ALT_ATTR, 
	                SRC_ATTR);
    	setServicesToChecker(ec);
    	ec.check(sspHandler, getElements(), testSolutionHandler);
    	
    	if(!titleElement.isEmpty()) {
    		ec = new TextNotIdenticalToAttributeChecker(
                	new TextAttributeOfElementBuilder(ALT_ATTR),
                	new TextAttributeOfElementBuilder(TITLE_ATTR),
                	new ImmutablePair(TestSolution.NEED_MORE_INFO, ""),
                	new ImmutablePair(TestSolution.FAILED, TITLE_NOT_IDENTICAL_TO_ALT_MSG),
                	ALT_ATTR,SRC_ATTR,TITLE_ATTR);
        	setServicesToChecker(ec);
    		ec.check(sspHandler, titleElement, testSolutionHandler);
    	}
    	
    	if(!ariaLabelElement.isEmpty()) {
    		ec = new TextNotIdenticalToAttributeChecker(
                	new TextAttributeOfElementBuilder(ALT_ATTR),
                	new TextAttributeOfElementBuilder(ARIA_LABEL_ATTR),
                	new ImmutablePair(TestSolution.NEED_MORE_INFO, ""),
                	new ImmutablePair(TestSolution.FAILED, ARIA_LABEL_NOT_IDENTICAL_TO_ALT_MSG),
                	ALT_ATTR,SRC_ATTR,ARIA_LABEL_ATTR);
        	setServicesToChecker(ec);
    		ec.check(sspHandler, ariaLabelElement, testSolutionHandler);
    	}
    	
    	if(!ariaLabelledbyElement.isEmpty()) {    		
    		ec = new TextNotIdenticalToAttributeChecker(
                	new TextAttributeOfElementBuilder(ALT_ATTR),
                	new TextAttributeOfElementBuilder(ARIA_LABELLEDBY_ATTR),
                	new ImmutablePair(TestSolution.NEED_MORE_INFO, ""),
                	new ImmutablePair(TestSolution.FAILED, ARIA_LABELLEDBY_NOT_INDENTICAL_TO_ALT_MSG),
                	ALT_ATTR,SRC_ATTR,ARIA_LABELLEDBY_ATTR);
        	setServicesToChecker(ec);
    		ec.check(sspHandler, ariaLabelledbyElement, testSolutionHandler);
    	}
    	
    }
}
