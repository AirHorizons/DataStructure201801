import java.io.*;
import java.util.*;

public class SortingTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try
		{
			boolean isRandom = false;	// 입력받은 배열이 난수인가 아닌가?
			int[] value;	// 입력 받을 숫자들의 배열
			String nums = br.readLine();	// 첫 줄을 입력 받음
			if (nums.charAt(0) == 'r')
			{
				// 난수일 경우
				isRandom = true;	// 난수임을 표시

				String[] nums_arg = nums.split(" ");

				int numsize = Integer.parseInt(nums_arg[1]);	// 총 갯수
				int rminimum = Integer.parseInt(nums_arg[2]);	// 최소값
				int rmaximum = Integer.parseInt(nums_arg[3]);	// 최대값

				Random rand = new Random();	// 난수 인스턴스를 생성한다.

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 각각의 배열에 난수를 생성하여 대입
					value[i] = rand.nextInt(rmaximum - rminimum + 1) + rminimum;
			}
			else
			{
				// 난수가 아닐 경우
				int numsize = Integer.parseInt(nums);

				value = new int[numsize];	// 배열을 생성한다.
				for (int i = 0; i < value.length; i++)	// 한줄씩 입력받아 배열원소로 대입
					value[i] = Integer.parseInt(br.readLine());
			}

			// 숫자 입력을 다 받았으므로 정렬 방법을 받아 그에 맞는 정렬을 수행한다.
			while (true)
			{
				int[] newvalue = (int[])value.clone();	// 원래 값의 보호를 위해 복사본을 생성한다.

				String command = br.readLine();

				long t = System.currentTimeMillis();
				switch (command.charAt(0))
				{
					case 'B':	// Bubble Sort
						newvalue = DoBubbleSort(newvalue);
						break;
					case 'I':	// Insertion Sort
						newvalue = DoInsertionSort(newvalue);
						break;
					case 'H':	// Heap Sort
						newvalue = DoHeapSort(newvalue);
						break;
					case 'M':	// Merge Sort
						newvalue = DoMergeSort(newvalue);
						break;
					case 'Q':	// Quick Sort
						newvalue = DoQuickSort(newvalue);
						break;
					case 'R':	// Radix Sort
						newvalue = DoRadixSort(newvalue);
						break;
					case 'X':
						return;	// 프로그램을 종료한다.
					default:
						throw new IOException("잘못된 정렬 방법을 입력했습니다.");
				}
				if (isRandom)
				{
					// 난수일 경우 수행시간을 출력한다.
					System.out.println((System.currentTimeMillis() - t) + " ms");
				}
				else
				{
					// 난수가 아닐 경우 정렬된 결과값을 출력한다.
					for (int i = 0; i < newvalue.length; i++)
					{
						System.out.println(newvalue[i]);
					}
				}

			}
		}
		catch (IOException e)
		{
			System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoBubbleSort(int[] value)
	{
		// value는 정렬안된 숫자들의 배열이며 value.length 는 배열의 크기가 된다.
		// 결과로 정렬된 배열은 리턴해 주어야 하며, 두가지 방법이 있으므로 잘 생각해서 사용할것.
		// 주어진 value 배열에서 안의 값만을 바꾸고 value를 다시 리턴하거나
		// 같은 크기의 새로운 배열을 만들어 그 배열을 리턴할 수도 있다.
    for (int i=0; i<value.length-1; i++)
    {
      for (int j=i; j<value.length-1; j++)
      {
        if (value[j]>value[j+1])
        {
          int temp = value[j];
          value[j] = value[j+1];
          value[j+1] = temp;
        }
      }
    }
		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoInsertionSort(int[] value)
	{
    int i, j, n;
    for (i = 0; i < value.length; i++)
    {
      n = value[i];
      j = i - 1;

      while(j >= 0 && value[j] > n)
      {
        value[j+1] = value[j];
        j = j - 1;
      }
      value[j+1] = n;
    }

		return (value);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoHeapSort(int[] value)
	{
    for (int i = value.length/2 - 1; i >= 0; i--)
    {
      MakeHeap(value, value.length, i);
    }
    for (int i = value.length - 1; i >= 0; i--)
    {
      int temp = value[0];
      value[0] = value[i];
      value[i] = temp;
      MakeHeap(value, i, 0);
    }
		return (value);
	}

  private static void MakeHeap(int[] value, int size, int n)
  {
    int min = n;
    int left = 2 * min + 1;
    int right = 2 * min + 2;

    if (left < size && value[left] < value[min])
    {
      min = left;
    }
    
    if (right < size && value[right] < value[min])
    {
      min = right;
    }

    if (min != n)
    {
      int temp = value[min];
      value[min] = value[n];
      value[n] = temp;
      MakeHeap(value, size, min);
    }
    return;
  }

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoMergeSort(int[] value)
	{
    MergeSort_R(value, 0, value.length-1);
		return (value);
	}

  private static void MergeSort_R(int[] value, int low, int high)
  {
    if (low<high)
    {
      int mid= low + (high-low)/2;
      MergeSort_R(value, low, mid);
      MergeSort_R(value, mid+1, high);
      Merge(value, low, mid, high);
    }
  }

  private static void Merge(int[] value, int low, int mid, int high)
  {
    int[] temparr = new int[high - low +1];
    int i = low, j = mid+1;
    while((i<mid+1)||(j<high+1))
    {
      if (j == high+1)
      {
        temparr[i + j - low - mid - 1] = value[i++];
      }
      else if (i == mid+1)
      {
        temparr[i + j - low - mid - 1] = value[j++];
      }
      else if (value[i]<value[j])
      {
        temparr[i + j - low - mid - 1] = value[i++];
      }
      else
        temparr[i + j - low - mid - 1] = value[j++];
    }
    for (i = low; i <= high; i++)
    {
      value[i] = temparr[i - low];
    }
  }

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoQuickSort(int[] value)
	{
    QuickSort_R(value, 0, value.length);
		return (value);
	}

  private static void QuickSort_R(int[] value, int low, int high)
  {
    if (low < high)
    {
      int p = Partition(value, low, high);
      QuickSort_R(value, low, p);
      QuickSort_R(value, p+1, high);
    }
    return;
  }

  private static int Partition(int[] value, int low, int high)
  {
    int i = low-1, j, pivot = value[high-1];

    for (j=low; j<high-1; j++)
    {
      if (value[j]<pivot)
      {
        i++;
        int temp = value[i];
        value[i] = value[j];
        value[j] = temp;
      }
    }
    int temp = value[i+1];
    value[i+1] = value[high-1];
    value[high-1] = temp;
    
    return i+1;
  }

	////////////////////////////////////////////////////////////////////////////////////////////////////
	private static int[] DoRadixSort(int[] value)
	{
    int m = FindMax(value);

    for (int exp=1; m/exp>0; exp*=10)
    {
      value = RadixStep(value, exp);
    }
    value = RadixStep(value); // sort for minus sign
		return (value);
	}

  private static int[] RadixStep(int[] value, int exp)
  {
    int result[] = new int[value.length];
    int count[] = new int[10];
    int i;
    
    Arrays.fill(count, 0);
    for (i=0; i<value.length; i++)
    {
      count[((value[i] / exp) % 10) + (((value[i]/exp) % 10 >= 0) ? 0 : 10)]++;
    }
    for (i=1; i<10; i++)
    {
      count[i] += count[i-1];
    }
    for (i=value.length-1; i>=0; i--)
    {
      result[count[((value[i] / exp) % 10) + (((value[i]/exp) % 10 >= 0) ? 0 : 10)] - 1] = value[i];
      count[((value[i] / exp) % 10) + (((value[i]/exp) % 10 >= 0) ? 0 : 10)]--;
    }
    return (result);
  }

  private static int[] RadixStep(int[] value)
  {
    int result[] = new int[value.length];
    int count[] = new int[2];
    int i, numneg; // numneg for counting negative number

    count[0] = 0;
    for (i=0; i<value.length; i++)
    {
      if (value[i] < 0) count[0]++;
    }
    numneg = count[0];
    count[1] = value.length;
    for (i=value.length-1; i>=0; i--)
    {
      if (value[i] < 0) 
      {
        result[count[0]-1] = value[i];
        count[0]--;
      }
      else
      {
        result[count[1] - 1] = value[i];
        count[1]--;
      }
    }
    return (result);
  }

  private static int FindMax(int[] value)
  {
    int max = Math.abs(value[0]);
    for (int i=1; i<value.length; i++)
    {
      int x = Math.abs(value[i]);
      if (x > max)
        max = x;
    }
    return max;
  }
}
