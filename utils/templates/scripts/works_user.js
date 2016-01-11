$(document).ready(function(){
    // the "href" attribute of .modal-trigger must specify the modal ID that wants to be triggered
    $('.modal-trigger').leanModal();
  });

$('#modal1').openModal();

$('#modal1').closeModal();

$('.modal-trigger').leanModal({
      dismissible: false, // Modal can be dismissed by clicking outside of the modal
      opacity: .5, // Opacity of modal background
      in_duration: 300, // Transition in duration
      out_duration: 200, // Transition out duration
      ready: function() {}, // Callback for Modal open
      complete: function() {} // Callback for Modal close
    }
  );