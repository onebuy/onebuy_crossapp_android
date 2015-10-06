package oneBuy.Util;

import oneBuy.Response.SondaPedidoResponse;

public class TaskCompleteSondaPedido {
	public OnTaskCompleted onTaskListenerComplete;

	public TaskCompleteSondaPedido(OnTaskCompleted onTaskListenerComplete)
	{
		if(onTaskListenerComplete != null)
		{
			this.setOnTaskComplete(onTaskListenerComplete);
		}
	}
	
	   public interface OnTaskCompleted {
	        void onTaskCompleted(SondaPedidoResponse objSondaPedidoResponse);
	   }

	   private void setOnTaskComplete(OnTaskCompleted onTaskListenerComplete){
	        this.onTaskListenerComplete = onTaskListenerComplete;
	   }
}
