package oneBuy.Util;

import oneBuy.Response.PedidoResponse;

public class TaskCompleteCriarPedido {
	public OnTaskCompleted onTaskListenerComplete;

	public TaskCompleteCriarPedido(OnTaskCompleted onTaskListenerComplete)
	{
		if(onTaskListenerComplete != null)
		{
			this.setOnTaskComplete(onTaskListenerComplete);
		}
	}
	
	   public interface OnTaskCompleted {
	        void onTaskCompleted(PedidoResponse objPedidoResponse);
	   }

	   private void setOnTaskComplete(OnTaskCompleted onTaskListenerComplete){
	        this.onTaskListenerComplete = onTaskListenerComplete;
	   }
}
