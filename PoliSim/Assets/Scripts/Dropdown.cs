using UnityEngine;
using TMPro;
using System.Collections.Generic;

public class Dropdown : MonoBehaviour
{
    public TMP_Dropdown dropdown;    // Assign in Inspector
    public TextAsset jsonFile;   // Drag your items.json here in Inspector

    private List<ItemData> itemList;

    void Start()
    {
        string wrappedJson = "{\"items\":" + jsonFile.text + "}";
        ItemDataList dataList = JsonUtility.FromJson<ItemDataList>(wrappedJson);
        itemList = dataList.items;

        dropdown.ClearOptions();
        List<string> optionTexts = new List<string>();

        List<string> IDs = ConvertList(GenerateUniqueRandomNumbers(10, 1, 350));
        foreach (ItemData item in itemList)
        {
            if (IDs.Contains((item.id).ToString()))
            {
                optionTexts.Add(item.text.Split(".")[0]);
            }
        }

        dropdown.AddOptions(optionTexts);
        dropdown.onValueChanged.AddListener(OnDropdownValueChanged);
    }

    void OnDropdownValueChanged(int index)
    {
        ItemData selected = itemList[index];
        Debug.Log($"Selected ID: {selected.id}, Text: {selected.text}");
    }
    
    List<int> GenerateUniqueRandomNumbers(int count, int minInclusive, int maxInclusive)
    {
        List<int> allNumbers = new List<int>();
        for (int i = minInclusive; i <= maxInclusive; i++)
        {
            allNumbers.Add(i);
        }

        // Shuffle the list
        for (int i = 0; i < allNumbers.Count; i++)
        {
            int randIndex = Random.Range(i, allNumbers.Count);
            int temp = allNumbers[i];
            allNumbers[i] = allNumbers[randIndex];
            allNumbers[randIndex] = temp;
        }

        // Take the first `count` numbers
        return allNumbers.GetRange(0, count);
    }

    List<string> ConvertList(List<int> numbers){
        List<string> stringList = new List<string>();
        foreach (int i in numbers)
        {
            stringList.Add("bill_" + i.ToString());
        }
        return stringList;
    }
}
[System.Serializable]
public class ItemData
{
    public string id;
    public string text;
}

[System.Serializable]
public class ItemDataList
{
    public List<ItemData> items;
}
