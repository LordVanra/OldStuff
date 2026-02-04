function openAdditionalExample_callback(app, hObject, ~)
            % Copyright 2018 The MathWorks Inc.
            % Open examples for the support package made by the app creator
            % when the user double clicks on the name of the example in the
            % listbox
            
            % use the UserData of the listbox to store information about
            % when it was last clicked on.  This will be used to determine
            % if a selection was double-clicked.
            currentTime = clock;
            currentSelectionIndex = get(hObject, 'Value');

            % get the listbox data
            listboxData = get(hObject, 'UserData');
            if(~isstruct(listboxData))
                listboxData.prevTime = zeros(1, 6);
                listboxData.prevSelectionIndex = -2;
            end
            
            if(isequal(currentSelectionIndex, listboxData.prevSelectionIndex) ...
            && etime(currentTime, listboxData.prevTime) <= 0.75)
                % on a double-click open example
                allValues = get(hObject, 'String');
                currentSelection = allValues{currentSelectionIndex};
                examplePath = vexapp.getAdditionalExamplePath(currentSelection);
                if(~isempty(examplePath))
                    supportPkg.openExample(examplePath)
                end
            end

            % update the listboxData
            listboxData.prevTime = currentTime;
            listboxData.prevSelectionIndex = currentSelectionIndex;
            set(hObject, 'UserData', listboxData);
        end