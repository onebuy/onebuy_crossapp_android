package oneBuy.Util;

import oneBuy.Response.PedidoResponse;

public class InterfaceCriarPedido {
	public OnTaskCompleted onTaskListenerComplete;

	public InterfaceCriarPedido(OnTaskCompleted onTaskListenerComplete)
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
