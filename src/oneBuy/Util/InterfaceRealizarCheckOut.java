package oneBuy.Util;

import oneBuy.Response.CheckoutOneBuyDadosResponse;

public class InterfaceRealizarCheckOut {
	public OnTaskCompleted onTaskListenerComplete;

	public InterfaceRealizarCheckOut(OnTaskCompleted onTaskListenerComplete)
	{
		if(onTaskListenerComplete != null)
		{
			this.setOnTaskComplete(onTaskListenerComplete);
		}
	}
	
	   public interface OnTaskCompleted {
	        void onTaskCompleted(CheckoutOneBuyDadosResponse objCheckoutOneBuyDadosResponse);
	   }

	   private void setOnTaskComplete(OnTaskCompleted onTaskListenerComplete){
	        this.onTaskListenerComplete = onTaskListenerComplete;
	   }
}
