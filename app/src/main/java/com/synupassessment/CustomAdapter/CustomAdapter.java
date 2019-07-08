package com.synupassessment.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.synupassessment.R;
import com.synupassessment.model.ExcludeList;
import com.synupassessment.model.MenuList;
import com.synupassessment.model.VariantGroup;
import com.synupassessment.model.Variation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private MenuList menuList;

    private Map<Integer, Object> selectedObjectMap;

    public CustomAdapter(Context context , MenuList menuList)
    {
        this.menuList = menuList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);

        selectedObjectMap = new HashMap<>();

    }


    @Override
    public int getCount() {
        return menuList.getVariants().getVariantGroups().size();
    }

    @Override
    public Object getItem(int i) {
        return menuList.getVariants().getVariantGroups().get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int pos, View covertview, ViewGroup parent) {

        View vi = covertview;
        final ViewHolder viewHolder;
        if(covertview == null)
        {

            vi = layoutInflater.inflate(R.layout.innerlayout,null);
            viewHolder = new ViewHolder();
            viewHolder.firstname = (TextView)vi.findViewById(R.id.firstname);
            viewHolder.menuGroup = vi.findViewById(R.id.menu_group);

            vi.setTag(viewHolder);

        }
        else {
            viewHolder = (ViewHolder)vi.getTag();
        }
        final VariantGroup items = menuList.getVariants().getVariantGroups().get(pos);
        viewHolder.firstname.setText(items.getName());
        viewHolder.menuGroup.removeAllViews();

        for (Variation variation: items.getVariations()) {
            final RadioButton rdbtn = new RadioButton(context);
//            rdbtn.setId(View.generateViewId());
            rdbtn.setId(Integer.parseInt(variation.getId()));
            SelectedObject object = new SelectedObject(items.getGroupId(), variation.getId(), rdbtn);
            rdbtn.setTag(object);
            rdbtn.setText(variation.getName());
            viewHolder.menuGroup.addView(rdbtn);

            rdbtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                    if(isNonValidSelection(rdbtn.getTag(), selectedObjectMap)) {
                        Toast.makeText(context, "Combination not available", Toast.LENGTH_SHORT).show();
                        selectedObjectMap.remove(items.getGroupId());
                        rdbtn.setChecked(false);
//                        if(viewHolder.menuGroup.getCheckedRadioButtonId() == -1) {
                            setCheck(items.getGroupId(), selectedObjectMap);
//                        }
                    } else {
                        if (isChecked) {
//                            Toast.makeText(context, String.valueOf(rdbtn.getId()), Toast.LENGTH_SHORT).show();
                            selectedObjectMap.put(Integer.parseInt(items.getGroupId()), rdbtn.getTag());
                        } else {
                            selectedObjectMap.remove(items.getGroupId());
                        }
                    }
                }
            });
        }

        return vi;
    }


    private void setCheck(String groudId, Map<Integer, Object> selectedObjectMap) {

        for(Map.Entry<Integer, Object> entry: selectedObjectMap.entrySet()) {

            int key = entry.getKey();
            if(key == Integer.parseInt(groudId)) {
                SelectedObject selectedObject = (SelectedObject) entry.getValue();
                selectedObject.rb.setChecked(true);
                return;
            }

        }

    }


  public static class ViewHolder
  {
      TextView firstname;
      RadioGroup menuGroup;
  }

  public class SelectedObject {
        String groupId;
        String varationId;
        RadioButton rb;

      public SelectedObject(String groupId, String varationId, RadioButton rb) {
          this.groupId = groupId;
          this.varationId = varationId;
          this.rb = rb;
      }

      public RadioButton getRb() {
          return rb;
      }

      public void setRb(RadioButton rb) {
          this.rb = rb;
      }

      public String getGroupId() {
          return groupId;
      }

      public void setGroupId(String groupId) {
          this.groupId = groupId;
      }

      public String getVarationId() {
          return varationId;
      }

      public void setVarationId(String varationId) {
          this.varationId = varationId;
      }
  }


  private boolean isNonValidSelection(Object currentSelection, Map<Integer, Object> selectedObj) {

        SelectedObject current = (SelectedObject) currentSelection;

        for(Map.Entry<Integer, Object> entry: selectedObj.entrySet()) {
            if(entry.getKey() !=  Integer.parseInt(current.varationId)) {
                SelectedObject alreadySelected = (SelectedObject) entry.getValue();
                if(isNonValid(current, alreadySelected)) {
                    return true;
                }
            }
        }

        return false;
  }

  private boolean isNonValid(SelectedObject current, SelectedObject alreadySelected) {

        List<List<ExcludeList>> excludedList = menuList.getVariants().getExcludeList();

        for(List<ExcludeList> list: excludedList) {
            int count = 0;
            for(ExcludeList exclude: list) {

                if(exclude.getVariationId().equals(current.getVarationId())) {
                    count ++;
                } else if (exclude.getVariationId().equals(alreadySelected.getVarationId())){
                    count ++;
                }

            }
            if(count == 2) {
                return true;
            }
        }

        return false;
  }


}
