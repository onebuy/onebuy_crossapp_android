package oneBuy.Util;

import oneBuy.Response.CheckoutOneBuyDadosResponse;

public class TaskCompleteRealizarCheckOut {
	public OnTaskCompleted onTaskListenerComplete;

	public TaskCompleteRealizarCheckOut(OnTaskCompleted onTaskListenerComplete)
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
