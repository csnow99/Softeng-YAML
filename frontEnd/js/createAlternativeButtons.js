function createAlternativeButtons(alternativeID, alternativeDiv) {
    let form = document.registerForm;

    // creating buttons element
    let upvoteButton = document.createElement('button');
    let downvoteButton = document.createElement('button');

    // creating text to be
    //displayed on buttons
    let upvoteText = document.createTextNode("Upvote");
    let downvoteText = document.createTextNode("Downvote");

    // appending text to button
    upvoteButton.appendChild(upvoteText);
    downvoteButton.appendChild(downvoteText);

    upvoteButton.onclick(amendVote(alternativeID, form.partName.value, 1));
    downvoteButton.onclick(amendVote(alternativeID, form.partName.value, 0));

    // appending button to div
    alternativeDiv.appendChild(upvoteButton);
    alternativeDiv.appendChild(downvoteButton);
}