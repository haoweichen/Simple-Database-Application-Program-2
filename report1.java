import java.sql.*;
public class report1 {

	public static void main(String[] args)
	{
		String usr ="postgres";
		String pwd ="";
		String url ="jdbc:postgresql://localhost:5432/postgres";

		try
		{
			Class.forName("org.postgresql.Driver");
			System.out.println("Success loading Driver!");
		}

		catch(Exception e)
		{
			System.out.println("Fail loading Driver!");
			e.printStackTrace();
		}

		try
		{
			Connection conn = DriverManager.getConnection(url, usr, pwd);
			System.out.println("Success connecting server!");

			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM Sales");
			
			
			String[] custs = new String[50];
			String[] prods = new String[50];
			
			int[] sum = new int[50];
			int[] otherprod_sum = new int[50];
			int[] othercust_sum = new int[50];
			int[] count = new int[50];
			int[] otherprod_count = new int[50];
			int[] othercust_count = new int[50];
			
			while (rs.next())
			{
				//figure out if the data is in the array
				boolean IsNew = true;
				int dbQuant = Integer.parseInt(rs.getString("quant"));
				int i = 0;
				for (i = 0; i < prods.length && prods[i] != null; i++){
					if (rs.getString("cust").equals(custs[i]) && rs.getString("prod").equals(prods[i]))
					{
						IsNew = false;
						count[i]++;
						sum[i] = sum[i] + dbQuant;
					}else if (rs.getString("cust").equals(custs[i]) && !rs.getString("prod").equals(prods[i]))
					{
						otherprod_sum[i] = otherprod_sum[i] + dbQuant;
						otherprod_count[i]++;
					}else if ((!rs.getString("cust").equals(custs[i])) && rs.getString("prod").equals(prods[i]))
					{
						othercust_sum[i] = othercust_sum[i] + dbQuant;
						othercust_count[i]++;
					}
				}
				// the data was not in the array before
				if (IsNew)
				{
					custs[i] = rs.getString("cust");
					prods[i] = rs.getString("prod");
					sum[i] = dbQuant;
					count[i]++;
					for (int j = 0; j < i; j++){
						if (rs.getString("cust").equals(custs[j]))
						{
							otherprod_sum[i] += sum[j];
							otherprod_count[i] += count[j];
						}else if (rs.getString("prod").equals(prods[j]))
						 {
							othercust_sum[i] += sum[j];
							othercust_count[i] += count[j];
						 }
					}
				}
			}
			for(int i = 0; i < 50; i++)
			{
					System.out.println(custs[i] + " " + prods[i] + " " + sum[i]/count[i] + " " +
						   	otherprod_sum[i]/otherprod_count[i] + " " + othercust_sum[i]/othercust_count[i]);
			}
		}
		
		catch(SQLException e)
		{
			System.out.println("Connection URL or username or password errors!");
			e.printStackTrace();
		}

	}

}