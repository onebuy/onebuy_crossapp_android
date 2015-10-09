package oneBuy.Util;

import oneBuy.Response.RetornoResponse;

public class InterfaceAbrirAppOneBuy {
   public OnTaskCompleted onTaskListenerComplete;

   public InterfaceAbrirAppOneBuy(OnTaskCompleted onTaskListenerComplete)
	{
		if(onTaskListenerComplete != null)
		{
			this.setOnTaskComplete(onTaskListenerComplete);
		}
	}
   
   public interface OnTaskCompleted {
        void onTaskCompleted(RetornoResponse objRetornoResponse);
   }

   private void setOnTaskComplete(OnTaskCompleted onTaskListenerComplete){
        this.onTaskListenerComplete = onTaskListenerComplete;
   }
}


	
	
