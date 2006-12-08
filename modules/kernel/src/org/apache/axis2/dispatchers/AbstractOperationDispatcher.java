/*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package org.apache.axis2.dispatchers;

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.description.AxisOperation;
import org.apache.axis2.description.AxisService;
import org.apache.axis2.description.HandlerDescription;
import org.apache.axis2.handlers.AbstractHandler;
import org.apache.axis2.i18n.Messages;
import org.apache.axis2.wsdl.WSDLConstants;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class AbstractOperationDispatcher extends AbstractHandler {

    public static final String NAME = "AbstractOperationDispatcher";
    private static final Log log = LogFactory.getLog(AbstractOperationDispatcher.class);
    private static final boolean isDebugEnabled = log.isDebugEnabled();

    public AbstractOperationDispatcher() {
        init(new HandlerDescription(NAME));
    }

    /**
     * Called by Axis Engine to find the operation.
     *
     * @param service
     * @param messageContext
     * @return Returns AxisOperation.
     * @throws AxisFault
     */
    public abstract AxisOperation findOperation(AxisService service, MessageContext messageContext)
            throws AxisFault;

    public abstract void initDispatcher();

    /**
     * @param msgctx
     * @throws org.apache.axis2.AxisFault
     */
    public InvocationResponse invoke(MessageContext msgctx) throws AxisFault {
         if ((msgctx.getAxisService() != null) && (msgctx.getAxisOperation() == null)) {
            AxisOperation axisOperation = findOperation(msgctx.getAxisService(), msgctx);

            if (axisOperation != null) {
                if (isDebugEnabled) {
                    log.debug(Messages.getMessage("operationfound",
                            axisOperation.getName().getLocalPart()));
                }

                msgctx.setAxisOperation(axisOperation);
                //setting axisMessage into messageContext
                msgctx.setAxisMessage(axisOperation.getMessage(
                        WSDLConstants.MESSAGE_LABEL_IN_VALUE));
            }
        }
        return InvocationResponse.CONTINUE;
    }
}
